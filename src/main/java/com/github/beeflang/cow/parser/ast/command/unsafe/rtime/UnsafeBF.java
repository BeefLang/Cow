package com.github.beeflang.cow.parser.ast.command.unsafe.rtime;

import com.github.beeflang.cow.app.Application;
import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.parser.ast.AST;
import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.command.unsafe.RuntimeUnsafe;

public record UnsafeBF(BFBuilder code) implements RuntimeUnsafe {
    @Override
    public void compile(AST ast, BFBuilder builder, ArgumentChain args) {
        Application.debug("Adding BF code: " + code);
        builder.addCode(code);
    }
}
