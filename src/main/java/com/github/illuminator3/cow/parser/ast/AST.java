package com.github.illuminator3.cow.parser.ast;

import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.compiler.Compilable;

import java.util.List;

public record AST(List<Macro> macros, FileAST file) implements Compilable<Void> {
    @Override
    public void compile(AST ast, BFBuilder builder, Void v) {
        file.compile(ast, builder, v);
    }
}