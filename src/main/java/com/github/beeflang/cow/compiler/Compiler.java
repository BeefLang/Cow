package com.github.beeflang.cow.compiler;

import com.github.beeflang.cow.parser.ast.AST;

import java.util.List;

public class Compiler {
    public String compile(AST ast) {
        BFBuilder bf = new BFBuilder();

        ast.compile(ast, bf, null);

        return optimize(bf.toString());
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