package com.github.illuminator3.cow.parser.ast.command.unsafe;

import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.parser.ast.AST;
import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.CArgument;

import java.util.List;

public non-sealed interface CompileTimeUnsafe extends CallUnsafe {
    @Override
    default boolean isCompileTimeInfo() {
        return true;
    }

    @Override
    default void compile(AST ast, BFBuilder builder, ArgumentChain args) {
    }
}
