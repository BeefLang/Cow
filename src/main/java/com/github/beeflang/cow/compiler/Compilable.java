package com.github.beeflang.cow.compiler;

import com.github.beeflang.cow.parser.ast.AST;

public interface Compilable<E> {
    void compile(AST ast, BFBuilder builder, E extra);
}