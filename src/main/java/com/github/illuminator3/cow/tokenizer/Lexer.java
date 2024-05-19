package com.github.illuminator3.cow.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lexer {
    public List<Line> readLines(String content, String file) {
        return readLines(content.split(System.lineSeparator()), file);
    }

    public List<Line> readLines(String[] content, String file) {
        return readLines(Arrays.asList(content), file);
    }

    public List<Line> readLines(List<String> content, String file) {
        return IntStream.range(0, content.size()).mapToObj(i -> new Line(content.get(i), file, i)).collect(Collectors.toList());
    }

    public List<Token> tokenize(List<Line> lines) {
        return lines.stream().map(line -> {
            List<Token> tokens = new ArrayList<>();

            int index = 0;
            String l = line.content();
            String content;

            lineLoop:
            while (!(content = l.substring(index)).isEmpty()) {
                tokenCheck:
                {
                    for (TokenType token : TokenType.values()) {
                        Matcher matcher = token.getRegex().matcher(content);

                        if (matcher.find()) {
                            if (token == TokenType.COMMENT) {
                                break lineLoop;
                            }

                            String found = matcher.group();

                            tokens.add(new Token(found, line, index, token));
                            index += found.length();

                            break tokenCheck;
                        }
                    }

                    throw new TokenizationException(
                            String.format(
                                    "%s%s%s |     %s%s%s |    %s%s %s [%s]",
                                    (line.index() <= 0 ? "" : System.lineSeparator() + " ".repeat(String.valueOf(line).length())),
                                    System.lineSeparator(),
                                    line.index() + 1,
                                    line.content(),
                                    System.lineSeparator(),
                                    " ".repeat(String.valueOf(line.index()).length()),
                                    " ".repeat("\t".length() + index),
                                    "^".repeat(content.length()),
                                    "Token not found",
                                    line.file()
                            )
                    );
                }
            }

            tokens.add(new Token("\n", line, -1, TokenType.NEWLINE));

            return tokens;
        }).flatMap(List::stream).collect(Collectors.toList());
    }
}