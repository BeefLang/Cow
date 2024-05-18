package com.github.illuminator3.cow.tokenizer;

import java.util.regex.Pattern;

public enum TokenType {
    // keywords
    DEFINE("def", "define"),
    // general tokens
    UNSAFE_CALL(Pattern.compile("^_\\w+"), "unsafe"),
    IDENTIFIER(Pattern.compile("^(?:@|(?=\\D)\\w)\\w+"), "identifier"),
    NEWLINE("\n", "newline"),
    WHITESPACE(Pattern.compile("^\\s+"), "whitespace"),
    OPEN_BRACES("{", "opening braces"),
    CLOSE_BRACES("}", "closing braces"),
    OPEN_BRACKETS("[", "opening brackets"),
    CLOSE_BRACKETS("]", "closing brackets"),
    OPEN_PARENTHESIS("(", "opening parenthesis"),
    CLOSE_PARENTHESIS(")", "closing parenthesis"),
    COLON(":", "colon"),
    SEMICOLON(";", "semicolon"),
    COMMA(",", "comma"),
    COMMENT("#", "comment"),
    EXCLAMATION_MARK("!", "exclamation mark"),
    // bf tokens
    BF_RIGHT(">", "brainfuck right"),
    BF_LEFT("<", "brainfuck left"),
    BF_PLUS("+", "brainfuck plus"),
    BF_MINUS("-", "brainfuck minus"),
//    BF_LOOP_START("[", "brainfuck loop start"),
//    BF_LOOP_END("]", "brainfuck loop end"),
    BF_PRINT(".", "brainfuck print"),
    BF_READ(",", "brainfuck read"),
    // other
    INTERNAL_REGISTER(Pattern.compile("^&\\d"), "internal register"),
    REGISTER(Pattern.compile("^%\\d"), "register"),
    ARGUMENT(Pattern.compile("^\\$\\d+"), "argument"),
    ANY_ADDRESS(Pattern.compile("^0y[\\da-fA-F]+"), "any address type"),
    ADDRESS(Pattern.compile("^0x[\\da-fA-F]+"), "address"),
    NUMBER(Pattern.compile("^\\d+"), "integer"),
    ;

    private final Pattern regex;
    private final String qualifiedName;

    TokenType(Pattern regex, String qualifiedName) {
        this.regex = regex;
        this.qualifiedName = qualifiedName;
    }

    TokenType(String spattern, String qualifiedName) {
        this(Pattern.compile("^\\Q" + spattern + "\\E"), qualifiedName);
    }

    public Pattern getRegex() {
        return regex;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }
}