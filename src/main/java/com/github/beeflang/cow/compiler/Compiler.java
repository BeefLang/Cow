package com.github.beeflang.cow.compiler;

import com.github.beeflang.cow.app.Application;
import com.github.beeflang.cow.parser.Parser;
import com.github.beeflang.cow.parser.ast.AST;
import com.github.beeflang.cow.tokenizer.Lexer;
import com.github.beeflang.cow.tokenizer.Line;
import com.github.beeflang.cow.tokenizer.Token;

import java.util.List;

public class Compiler {
    public static BFBuilder fullCompile(Lexer lexer, List<Line> lines) {
        Application.debug("Tokenization");

        List<Token> tokenize = lexer.tokenize(lines);

        Application.debug("Parsing");

        Parser parser = new Parser(tokenize);

        AST ast = parser.parse();

        Application.debug(ast);

        Application.debug("Compiling");

        return new Compiler().compile(ast);
    }

    public BFBuilder compile(AST ast) {
        BFBuilder bf = new BFBuilder();

        ast.compile(ast, bf, null);

        return BFBuilder.code(optimize(bf.toString()));
    }

    private String optimize(String s) {
        // Remove +- or -+
        // Remove >< or <>
        // Remove []
        List<String> patterns = List.of("\\+\\-", "\\-\\+", "\\>\\<", "\\<\\>", "\\[\\]");

        for (;;) {
            String prev = s;

            for (String pattern : patterns) {
                s = s.replaceAll(pattern, "");
            }

            if (prev.equals(s)) {
                break;
            }
        }

        return s;
    }
}