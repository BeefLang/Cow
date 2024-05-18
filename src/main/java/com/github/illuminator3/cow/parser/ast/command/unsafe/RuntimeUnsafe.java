package com.github.illuminator3.cow.parser.ast.command.unsafe;

public non-sealed interface RuntimeUnsafe extends CallUnsafe {
    @Override
    default boolean isCompileTimeInfo() {
        return false;
    }
}
