package com.github.beeflang.cow.parser.ast.command.unsafe.ctime;

import com.github.beeflang.cow.parser.ast.ASTLevel;
import com.github.beeflang.cow.parser.ast.command.unsafe.CompileTimeUnsafe;

import java.util.Set;

public class UnsafeEnableUnsafe implements CompileTimeUnsafe {
    @Override
    public boolean isUnsafe() {
        // In theory it's an unsafe
        return false;
    }

    @Override
    public Set<ASTLevel> permittedLevels() {
        return Set.of(ASTLevel.FILE);
    }
}
