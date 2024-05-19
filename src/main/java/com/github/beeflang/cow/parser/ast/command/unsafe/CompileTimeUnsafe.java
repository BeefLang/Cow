package com.github.beeflang.cow.parser.ast.command.unsafe;

import com.github.beeflang.cow.app.Application;
import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.parser.ast.AST;
import com.github.beeflang.cow.parser.ast.ArgumentChain;

public non-sealed interface CompileTimeUnsafe extends CallUnsafe {
    @Override
    default boolean isCompileTimeInfo() {
        return true;
    }

    @Override
    default void compile(AST ast, BFBuilder builder, ArgumentChain args) {
        Application.debug("Compiling compile time unsafe " + this);
    }
}
