package com.github.illuminator3.cow.compiler;

import com.github.illuminator3.cow.parser.ast.AST;

public interface Compilable<E> {
    void compile(AST ast, BFBuilder builder, E extra);
}