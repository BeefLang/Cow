package com.github.illuminator3.cow.parser.ast.command;

import com.github.illuminator3.cow.compiler.Compilable;
import com.github.illuminator3.cow.parser.ast.ASTLevel;
import com.github.illuminator3.cow.parser.ast.ArgumentChain;

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