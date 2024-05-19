package com.github.beeflang.cow.parser.ast;

import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.compiler.Compilable;

import java.util.List;

public record AST(List<Macro> macros, FileAST file) implements Compilable<Void> {
    @Override
    public void compile(AST ast, BFBuilder builder, Void v) {
        file.compile(ast, builder, v);
    }
}