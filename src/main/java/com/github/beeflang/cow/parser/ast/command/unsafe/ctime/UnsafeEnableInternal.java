package com.github.beeflang.cow.parser.ast.command.unsafe.ctime;

import com.github.beeflang.cow.parser.ast.ASTLevel;
import com.github.beeflang.cow.parser.ast.command.unsafe.CompileTimeUnsafe;

import java.util.Set;

public class UnsafeEnableInternal implements CompileTimeUnsafe {
    @Override
    public Set<ASTLevel> permittedLevels() {
        return Set.of(ASTLevel.MACRO);
    }
}
