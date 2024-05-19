package com.github.beeflang.cow.tokenizer;

import com.github.beeflang.cow.parser.ParseException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Token {
    private final String content;
    private final Line line;
    private final int index;
    private final TokenType type;

    public Token(String content, Line line, int index, TokenType type) {
        this.content = content;
        this.line = line;
        this.index = index;
        this.type = type;
    }

    public static List<Token> dummy(TokenType... types) {
        return Arrays.stream(types).map(Token::dummy).toList();
    }

    public static Token dummy(TokenType type) {
        return new Token("", null, 0, type);
    }

//    public int contentAsInt()
//    {
//        try
//        {
//            return Integer.parseInt(content);
//        } catch (NumberFormatException ignored) {}
//
//        return 0;
//    }

    public Token checkType(TokenType expected, String message)
            throws ParseException {
        if (!type.equals(expected)) error(message);

        return this;
    }

    public Token checkType(String message, TokenType... possible) {
        if (!Arrays.asList(possible).contains(type)) error(message);

        return this;
    }

    public Token checkType(TokenType expected) {
        return checkType(expected, "Expected " + expected.getQualifiedName());
    }

    public Token checkContent(String expected, String message) {
        if (!content.equals(expected)) error(message);

        return this;
    }

    public Token checkContent(String expected) {
        return checkContent(expected, "Expected '" + expected + "'");
    }

    public <T> T unexpected(String info) {
        error("Unexpected token: " + type, info);

        return null;
    }

    public <T> T unexpected()
            throws ParseException {
        return unexpected(null);
    }

    public <T> T error(String message)
            throws ParseException {
        error(message, null);

        return null;
    }

    public void error(String message, String info)
            throws ParseException {
        throw new ParseException(
                String.format(
                        "%s%s%s |     %s%s%s |    %s%s %s [%s]%s",
                        (line.index() <= 0 ? "" : System.lineSeparator() + " ".repeat(String.valueOf(line).length())),
                        System.lineSeparator(),
                        line.index() + 1,
                        line.content(),
                        System.lineSeparator(),
                        " ".repeat(String.valueOf(line.index()).length()),
                        " ".repeat("\t".length() + index),
                        "^".repeat(content.length()),
                        message,
                        line.file(),
                        info == null ? "" : System.lineSeparator() + "=info: " + info
                )
        );
    }

    public String content() {
        return content;
    }

    public Line line() {
        return line;
    }

    public int index() {
        return index;
    }

    public TokenType type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Token) obj;
        return Objects.equals(this.content, that.content) &&
                Objects.equals(this.line, that.line) &&
                this.index == that.index &&
                Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, line, index, type);
    }

    @Override
    public String toString() {
        return "Token[" +
                "content=" + content + ", " +
                "line=" + line + ", " +
                "index=" + index + ", " +
                "type=" + type + ']';
    }
}