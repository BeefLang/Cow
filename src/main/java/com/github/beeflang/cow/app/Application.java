package com.github.beeflang.cow.app;

import com.github.beeflang.cow.compiler.Compiler;
import com.github.beeflang.cow.parser.ast.ctypes.Address;
import com.github.beeflang.cow.tokenizer.Lexer;
import com.github.beeflang.cow.tokenizer.Line;
import com.github.beeflang.dairy.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class Application {
    public static boolean ENABLE_DEBUG = false;
    public static int N_INTERNAL_REGISTERS = 10, N_FUNCTION_REGISTERS = 10, MEMORY_SPACING = 5, ZERO_POSITION = new Address(0).translate().position();

    public static void main(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser(args);
        ParsedArguments arg = argumentParser.parse(
                ParseOptions.builder()
                        .argument(Argument.builder().name("verbose").optional(true).dataType(DataType.BOOLEAN).defaultValue("false").build())
                        .argument(Argument.builder().name("input").optional(false).dataType(DataType.FILE).build())
                        .argument(Argument.builder().name("output").optional(true).dataType(DataType.FILE).defaultValue("output.bf").build())
                        .argument(Argument.builder().name("internalRegisters").optional(true).dataType(DataType.INT).defaultValue("10").build())
                        .argument(Argument.builder().name("functionRegisters").optional(true).dataType(DataType.INT).defaultValue("10").build())
                        .build());

        ENABLE_DEBUG = arg.getContentInType("verbose");

        Path path = arg.getContentInType("input");
        Path output = arg.getContentInType("output");

        System.out.println("Input file: " + path.getFileName().toString());
        System.out.println("Output file: " + output.getFileName().toString());
        System.out.println("Verbose: " + ENABLE_DEBUG);

        Lexer lexer = new Lexer();

        Application.debug("Lexing preamble");

        List<Line> lines = null;

        try (InputStream resource = Application.class.getResourceAsStream("/preamble.cow");
             InputStreamReader inputStreamReader = new InputStreamReader(resource, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            lines = lexer.readLines(bufferedReader.lines().toList(), "preamble.cow");

            Application.debug("Lexing input file " + path);

            lines.addAll(lexer.readLines(Files.readAllLines(path), path.getFileName().toString()));
        } catch (NoSuchFileException e) {
            System.err.println("Error: Could not find file");

            e.printStackTrace();

            System.exit(-1);
        } catch (NullPointerException e) {
            System.err.println("Error: Internal error");

            e.printStackTrace();

            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();

            System.exit(-1);
        }

        System.out.println("Compiling...");

        String bf = Compiler.fullCompile(lexer, lines).toString();

        System.out.println(bf);

        try {
            Files.write(output, bf.lines().toList());
        } catch (IOException e) {
            System.err.println("Error: Could not write to output file");

            e.printStackTrace();

            System.exit(-1);
        }
    }

    public static void debug(Object x, Object... args) {
        if (ENABLE_DEBUG) {
            Application.debug(x.toString().formatted(args));
        }
    }
}