package com.github.beeflang.cow.parser;

import com.github.beeflang.cow.app.Application;
import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.parser.ast.*;
import com.github.beeflang.cow.parser.ast.command.CallMacro;
import com.github.beeflang.cow.parser.ast.command.Command;
import com.github.beeflang.cow.parser.ast.command.unsafe.CallUnsafe;
import com.github.beeflang.cow.parser.ast.command.unsafe.ctime.UnsafeEnableInternal;
import com.github.beeflang.cow.parser.ast.command.unsafe.ctime.UnsafeEnableUnsafe;
import com.github.beeflang.cow.parser.ast.command.unsafe.rtime.*;
import com.github.beeflang.cow.parser.ast.ctypes.*;
import com.github.beeflang.cow.tokenizer.Token;
import com.github.beeflang.cow.tokenizer.TokenType;

import java.util.*;
import java.util.stream.Collectors;

// TODO debug statements
public class Parser {
    private final Queue<Token> tokens;

    public Parser(List<Token> tokens) {
        this.tokens = new LinkedList<>(tokens.stream().filter(t -> t.type() != TokenType.WHITESPACE && t.type() != TokenType.NEWLINE).toList());
    }

    public AST parse() throws ParseException {
        try {
            return parse0();
        } catch (NullPointerException ex) {
            throw new ParseException("Reached EOF while parsing (missing semicolon?)");
        }
    }

    private AST parse0() {
        // List<Macro> macros, List<FileAST> files
        List<Macro> macros = new ArrayList<>();
        List<Command> commands = new ArrayList<>();

        Queue<Token> tmp = new LinkedList<>(tokens);

        // Macro peek
        while (!isEmpty()) {
            TokenType token = peek().type();

            if (token == TokenType.DEFINE) {
                macros.add(parseMacroHeader(macros));
            } else {
                remove();
            }
        }

        while (!isEmpty()) {
            remove();
        }

        tokens.addAll(tmp);

        while (!isEmpty()) {
            switch (peek().type()) {
                case DEFINE -> parseMacro(macros);
                case UNSAFE_CALL -> {
                    commands.add(parseUnsafeCall(macros, new CType[0]));
                    removeAndExpect(TokenType.SEMICOLON);
                }
                case IDENTIFIER -> commands.add(parseMacroCall(macros, new CType[0]));
                default -> remove().unexpected();
            }
        }

        return verify(new AST(macros, new FileAST(commands)));
    }

    private AST verify(AST ast) {
        // _ only if _unsafe was called TODO
        // @ only if _internal was called, same applies to macro parameters (@AnyAddress) TODO
        // _unsafe and _internal only in file, nowhere else DONE
        // check ast levels DONE

        for (Command cmd : ast.file().commands()) {
            if (!cmd.permittedLevels().contains(ASTLevel.FILE)) {
                throw new IllegalStateException("Use of command outside of accepted scope: " + cmd);
            }
        }

        List<Command> foundCommands = new ArrayList<>();
        Queue<Macro> macros = new LinkedList<>();

        macros.addAll(ast.macros());

        while (!macros.isEmpty()) {
            Macro next = macros.remove();

            for (Command cmd : next.commands()) {
                foundCommands.add(cmd);

                if (cmd instanceof CallMacro cm) {
                    macros.add(cm.macro());
                }
            }
        }

        for (Command cmd : foundCommands) {
            if (!cmd.permittedLevels().contains(ASTLevel.MACRO)) {
                throw new IllegalStateException("Use of command outside of accepted scope: " + cmd);
            }
        }

        return ast;
    }

    private CallUnsafe parseUnsafeCall(List<Macro> macros, CType[] possibleArgTypes) {
        Token peek = peek();

        peek.checkType(TokenType.UNSAFE_CALL);

        return switch (peek.content()) {
            // Compile time
            case "_unsafe" -> parseUnsafeUnsafe();
            case "_internal" -> parseInternalUnsafe();
            // Runtime
            case "_bf" -> parseBFUnsafe();
            case "_sub" -> parseSubUnsafe(macros, possibleArgTypes);
            case "_translateRegister" -> parseTranslateRegisterUnsafe(macros, possibleArgTypes);
            case "_translateAddress" -> parseTranslateAddressUnsafe(macros, possibleArgTypes);
            case "_ptrelt" -> parsePtreltUnsafe(macros, possibleArgTypes);
            case "_ptrelb" -> parsePtrelbUnsafe(macros, possibleArgTypes);
            default -> peek.error("Unknown unsafe");
        };
    }

    private UnsafeEnableUnsafe parseUnsafeUnsafe() {
        removeAndExpectContentType(TokenType.UNSAFE_CALL, "_unsafe");

        return new UnsafeEnableUnsafe();
    }

    private UnsafeEnableInternal parseInternalUnsafe() {
        removeAndExpectContentType(TokenType.UNSAFE_CALL, "_internal");

        return new UnsafeEnableInternal();
    }

    private UnsafeBF parseBFUnsafe() {
        removeAndExpectContentType(TokenType.UNSAFE_CALL, "_bf");

        BFBuilder code = parseBFCode();

        return new UnsafeBF(code);
    }

    private UnsafeSub parseSubUnsafe(List<Macro> macros, CType[] possibleArgTypes) {
        Token token = removeAndExpectContentType(TokenType.UNSAFE_CALL, "_sub");

        CArgument arg = parseMacroArgument(macros, possibleArgTypes);

        if (!(arg instanceof TypeOfCodeBlock)) {
            token.error("Expected type of code block");
        }

        return new UnsafeSub((TypeOfCodeBlock) arg);
    }

    private UnsafeTranslateRegister parseTranslateRegisterUnsafe(List<Macro> macros, CType[] possibleArgTypes) {
        Token token = removeAndExpectContentType(TokenType.UNSAFE_CALL, "_translateRegister");

        CArgument reg = parseMacroArgument(macros, possibleArgTypes);

        if (!(reg instanceof TypeOfRegister)) {
            token.error("Expected type of register");
        }

        return new UnsafeTranslateRegister((TypeOfRegister) reg);
    }

    private UnsafeTranslateAddress parseTranslateAddressUnsafe(List<Macro> macros, CType[] possibleArgTypes) {
        Token token = removeAndExpectContentType(TokenType.UNSAFE_CALL, "_translateAddress");

        CArgument adr = parseMacroArgument(macros, possibleArgTypes);

        if (!(adr instanceof TypeOfAddress)) {
            token.error("Expected type of address");
        }

        return new UnsafeTranslateAddress((TypeOfAddress) adr);
    }

    private UnsafePtrelt parsePtreltUnsafe(List<Macro> macros, CType[] possibleArgTypes) {
        Token token = removeAndExpectContentType(TokenType.UNSAFE_CALL, "_ptrelt");

        CArgument anyadr = parseMacroArgument(macros, possibleArgTypes);

        if (!(anyadr instanceof TypeOfAnyAddress)) {
            token.error("Expected type of any address");
        }

        return new UnsafePtrelt((TypeOfAnyAddress) anyadr);
    }

    private UnsafePtrelb parsePtrelbUnsafe(List<Macro> macros, CType[] possibleArgTypes) {
        Token token = removeAndExpectContentType(TokenType.UNSAFE_CALL, "_ptrelb");

        CArgument anyadr = parseMacroArgument(macros, possibleArgTypes);

        if (!(anyadr instanceof TypeOfAnyAddress)) {
            token.error("Expected type of any address");
        }

        return new UnsafePtrelb((TypeOfAnyAddress) anyadr);
    }

    private BFBuilder parseBFCode() {
        removeAndExpect(TokenType.EXCLAMATION_MARK);
        removeAndExpect(TokenType.OPEN_BRACES);

        BFBuilder builder = BFBuilder.empty();

        while (peek().type() != TokenType.CLOSE_BRACES) {
            Token next = remove();

            builder.addCode(next.checkType("Expected +, -, [, ], <, >, ., or ,; got " + next.type(), TokenType.BF_PLUS, TokenType.BF_MINUS, TokenType.OPEN_BRACKETS, TokenType.CLOSE_BRACKETS, TokenType.BF_LEFT, TokenType.BF_RIGHT, TokenType.BF_READ, TokenType.BF_PRINT).content());
        }

        removeAndExpect(TokenType.CLOSE_BRACES);

        builder.verify();

        return builder;
    }

    private CallMacro parseMacroCall(List<Macro> macros, CType[] possibleArgTypes) {
        Token nameToken = removeAndExpect(TokenType.IDENTIFIER);
        String macroName = nameToken.content();
        List<CArgument> arguments = new ArrayList<>();

        while (peek().type() != TokenType.SEMICOLON) {
            arguments.add(parseMacroArgument(macros, possibleArgTypes));
        }

        removeAndExpect(TokenType.SEMICOLON);

        List<CType> argumentTypes = arguments.stream().map(CArgument::getType).toList();

        Optional<Macro> first = macros.stream().filter(macro -> macro.name().equals(macroName)).filter(macro -> macro.parameters().equals(argumentTypes)).findFirst();

        if (first.isEmpty()) {
            Application.debug(macroName);
            Application.debug(argumentTypes);
            String args = argumentTypes.stream().map(CType::getName).collect(Collectors.joining(", "));
            nameToken.error("Macro not found", "Args: " + (args.isEmpty() ? "none" : args));
        }

        return new CallMacro(first.get(), arguments);
    }

    private CArgument parseMacroArgument(List<Macro> macros, CType[] possibleArgTypes) {
        // Either: code block, address, any address, function register, internal register, argument register, bf code, unsafe call
        boolean parens = optional(TokenType.OPEN_PARENTHESIS) != null;

        Token peek = peek();

        try {
            return switch (peek.type()) {
                case OPEN_BRACES -> parseCodeBlock(macros, possibleArgTypes);
                case ADDRESS -> parseAddress();
                case ANY_ADDRESS -> parseAnyAddress();
                case REGISTER -> parseFunctionRegister();
                case INTERNAL_REGISTER -> parseInternalRegister();
                case ARGUMENT -> parseArgumentRegister(possibleArgTypes);
                case EXCLAMATION_MARK -> parseBFCode();
                case UNSAFE_CALL -> {
                    CallUnsafe cu = parseUnsafeCall(macros, possibleArgTypes);

                    if (!(cu instanceof CArgument)) {
                        peek.error("Unsafe call does not return anything");
                    }

                    yield (CArgument) cu;
                }
                default -> peek.unexpected();
            };
        } finally {
            if (parens) {
                removeAndExpect(TokenType.CLOSE_PARENTHESIS);
            }
        }
    }

    private CodeBlock parseCodeBlock(List<Macro> macros, CType[] possibleArgTypes) {
        removeAndExpect(TokenType.OPEN_BRACES);

        List<Command> commands = new ArrayList<>();

        while (peek().type() != TokenType.CLOSE_BRACES) {
            switch (peek().type()) {
                case UNSAFE_CALL -> {
                    commands.add(parseUnsafeCall(macros, possibleArgTypes));
                    removeAndExpect(TokenType.SEMICOLON);
                }
                case IDENTIFIER -> commands.add(parseMacroCall(macros, possibleArgTypes));
                default -> remove().unexpected();
            }
        }

        removeAndExpect(TokenType.CLOSE_BRACES);

        return new CodeBlock(commands);
    }

    private Address parseAddress() {
        return new Address(Integer.parseInt(removeAndExpect(TokenType.ADDRESS).content().substring(2), 16));
    }

    private AnyAddress parseAnyAddress() {
        Application.debug("Parsing any address: " + peek().content());
        Application.debug("Value:" + Integer.parseInt(peek().content().substring(2), 16));
        return new AnyAddress(Integer.parseInt(removeAndExpect(TokenType.ANY_ADDRESS).content().substring(2), 16));
    }

    private CFunctionRegister parseFunctionRegister() {
        return new CFunctionRegister(Integer.parseInt(removeAndExpect(TokenType.REGISTER).content().substring(1)));
    }

    private CInternalRegister parseInternalRegister() {
        return new CInternalRegister(Integer.parseInt(removeAndExpect(TokenType.INTERNAL_REGISTER).content().substring(1)));
    }

    private Argument parseArgumentRegister(CType[] possibleArgTypes) {
        Token token = removeAndExpect(TokenType.ARGUMENT);
        int n = Integer.parseInt(token.content().substring(1));

        if (possibleArgTypes.length < n) {
            token.error("Argument out of bounds");
        }

        return switch (possibleArgTypes[n]) {
            case Code -> new TypeOfCodeBlockArgument(n);
            case Register -> new TypeOfRegisterArgument(n);
            case Address -> new TypeOfAddressArgument(n);
            case AnyAddress -> new TypeOfAnyAddressArgument(n);
            default -> token.error("Unexpected type");
        };
    }

    private void parseMacro(List<Macro> initialParsing) {
        removeAndExpect(TokenType.DEFINE);

        if (peek().type() == TokenType.OPEN_BRACKETS) {
            remove();
            removeAndExpect(TokenType.IDENTIFIER);
            removeAndExpect(TokenType.CLOSE_BRACKETS);
        }

        String name = removeAndExpect(TokenType.IDENTIFIER).content();

        List<CType> parameters;

        if (peek().type() != TokenType.OPEN_BRACES) {
            parameters = parseTypeList();
        } else {
            parameters = new ArrayList<>();
        }

        Macro macro = initialParsing.stream().filter(m -> m.name().equals(name)).filter(m -> m.parameters().equals(parameters)).findFirst().get();
        CodeBlock codeBlock = parseCodeBlock(initialParsing, parameters.toArray(CType[]::new));

        macro.updateCommands(codeBlock.commands());
    }

    private List<CType> parseTypeList() {
        List<CType> parameters = new ArrayList<>();

        removeAndExpect(TokenType.OPEN_PARENTHESIS);

        for (;;) {
            Token id = removeAndExpect(TokenType.IDENTIFIER);
            Optional<CType> type = CType.byName(id.content());

            if (type.isPresent()) {
                parameters.add(type.get());
            } else {
                id.error("Unknown type");
            }

            if (peek().type() == TokenType.CLOSE_PARENTHESIS) {
                break;
            }

            removeAndExpect(TokenType.COMMA);
        }

        removeAndExpect(TokenType.CLOSE_PARENTHESIS);

        return parameters;
    }

    private Macro parseMacroHeader(List<Macro> presentMacros) {
        removeAndExpect(TokenType.DEFINE);

        // TODO
        if (peek().type() == TokenType.OPEN_BRACKETS) {
            remove();
            removeAndExpect(TokenType.IDENTIFIER);
            removeAndExpect(TokenType.CLOSE_BRACKETS);
        }

        Token nameToken = removeAndExpect(TokenType.IDENTIFIER);
        String name = nameToken.content();

        boolean internal = name.startsWith("@");
        List<CType> parameters;

        if (peek().type() != TokenType.OPEN_BRACES) {
            parameters = parseTypeList();
        } else {
            parameters = new ArrayList<>();
        }

        removeAndExpect(TokenType.OPEN_BRACES);

        int braces = 1;

        while (braces != 0) {
            TokenType next = remove().type();

            if (next == TokenType.OPEN_BRACES) {
                braces++;
            } else if (next == TokenType.CLOSE_BRACES) {
                braces--;
            }
        }

        if (presentMacros.stream().filter(macro -> macro.name().equals(name)).anyMatch(macro -> macro.parameters().equals(parameters))) {
            nameToken.error("Macro already defined");
        }

        return new Macro(name, parameters, null, internal);
    }

    private boolean isEmpty() {
        return tokens.isEmpty();
    }

    private Token peek() {
        return tokens.peek();
    }

    private Token remove() {
        return tokens.remove();
    }

    private Token removeAndExpect(TokenType type) {
        return remove().checkType(type);
    }

    private Token removeAndExpectContentType(TokenType type, String content) {
        return removeAndExpect(type).checkContent(content);
    }

    private Token optional(TokenType type) {
        Token peek = peek();

        if (peek.type() == type) {
            return remove();
        }

        return null;
    }
}