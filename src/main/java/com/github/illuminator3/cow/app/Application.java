package com.github.illuminator3.cow.app;

import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.compiler.Compiler;
import com.github.illuminator3.cow.parser.Parser;
import com.github.illuminator3.cow.parser.ast.AST;
import com.github.illuminator3.cow.parser.ast.ctypes.Address;
import com.github.illuminator3.cow.tokenizer.Lexer;
import com.github.illuminator3.cow.tokenizer.Line;
import com.github.illuminator3.cow.tokenizer.Token;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Application {
    public static int N_INTERNAL_REGISTERS = 10, N_FUNCTION_REGISTERS = 10, MEMORY_SPACING = 5, ZERO_POSITION = new Address(0).translate().position();

    public static boolean ENABLE_DEBUG = false;

    public static void main(String[] args) throws IOException {
        Lexer lexer = new Lexer();
        List<Line> lines = lexer.readLines(Files.readAllLines(Path.of("src/main/resources/preamble.cow")), "preamble.cow");
        lines.addAll(lexer.readLines(Files.readAllLines(Path.of("src/main/resources/test.cow")), "test.cow"));
        List<Token> tokenize = lexer.tokenize(lines);

        Parser parser = new Parser(tokenize);

        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
            }
        }));

        AST ast = parser.parse();

        Application.debug(ast);

        Application.debug(new Compiler().compile(ast));
    }

    public static void debug(Object x, Object... args) {
        Application.debug(x.toString().formatted(args));
    }
}