package com.github.illuminator3.cow.parser.ast.command.unsafe;

import com.github.illuminator3.cow.parser.ast.command.Command;

public sealed interface CallUnsafe extends Command permits CompileTimeUnsafe, RuntimeUnsafe {
    boolean isCompileTimeInfo();

    @Override
    default boolean isUnsafe() {
        return true;
    }
}