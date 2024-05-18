package com.github.illuminator3.cow.parser.ast.command.unsafe.ctime;

import com.github.illuminator3.cow.parser.ast.ASTLevel;
import com.github.illuminator3.cow.parser.ast.command.unsafe.CompileTimeUnsafe;

import java.util.Set;

public class UnsafeEnableInternal implements CompileTimeUnsafe {
    @Override
    public Set<ASTLevel> permittedLevels() {
        return Set.of(ASTLevel.MACRO);
    }
}
