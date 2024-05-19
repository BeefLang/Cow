package com.github.beeflang.cow.app;

import com.github.beeflang.cow.compiler.Compiler;
import com.github.beeflang.cow.parser.Parser;
import com.github.beeflang.cow.parser.ast.AST;
import com.github.beeflang.cow.parser.ast.ctypes.Address;
import com.github.beeflang.cow.tokenizer.Lexer;
import com.github.beeflang.cow.tokenizer.Line;
import com.github.beeflang.cow.tokenizer.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Application {
    public static int N_INTERNAL_REGISTERS = 10, N_FUNCTION_REGISTERS = 10, MEMORY_SPACING = 5, ZERO_POSITION = new Address(0).translate().position();

    public static boolean ENABLE_DEBUG = false;

    public static void main(String[] args) throws IOException {
        Lexer lexer = new Lexer();

        Application.debug("Lexing preamble");

        List<Line> lines = lexer.readLines(Files.readAllLines(Path.of("src/main/resources/preamble.cow")), "preamble.cow");

        Application.debug("Lexing input file");

        lines.addAll(lexer.readLines(Files.readAllLines(Path.of("src/main/resources/test.cow")), "test.cow"));

        Application.debug("Tokenization");

        List<Token> tokenize = lexer.tokenize(lines);

        Application.debug("Parsing");

        Parser parser = new Parser(tokenize);

        AST ast = parser.parse();

        Application.debug(ast);

        Application.debug("Compiling");

        Application.debug(new Compiler().compile(ast));
    }

    public static void debug(Object x, Object... args) {
        if (ENABLE_DEBUG) {
            Application.debug(x.toString().formatted(args));
        }
    }
}