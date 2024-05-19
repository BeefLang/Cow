package com.github.beeflang.cow.parser.ast.command.unsafe;

public non-sealed interface RuntimeUnsafe extends CallUnsafe {
    @Override
    default boolean isCompileTimeInfo() {
        return false;
    }
}
