package com.github.beeflang.cow.parser.ast.command;

import com.github.beeflang.cow.compiler.Compilable;
import com.github.beeflang.cow.parser.ast.ASTLevel;
import com.github.beeflang.cow.parser.ast.ArgumentChain;

import java.util.Set;

public interface Command extends Compilable<ArgumentChain> {
    default boolean isUnsafe() {
        return false;
    }

    default boolean isInternal() {
        return false;
    }

    default Set<ASTLevel> permittedLevels() {
        return Set.of(ASTLevel.values());
    }
}