package com.github.illuminator3.cow.parser.ast.command.unsafe.ctime;

import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.parser.ast.AST;
import com.github.illuminator3.cow.parser.ast.ASTLevel;
import com.github.illuminator3.cow.parser.ast.CArgument;
import com.github.illuminator3.cow.parser.ast.command.unsafe.CompileTimeUnsafe;

import java.util.List;
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
