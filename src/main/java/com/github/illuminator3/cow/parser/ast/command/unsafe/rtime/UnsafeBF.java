package com.github.illuminator3.cow.parser.ast.command.unsafe.rtime;

import com.github.illuminator3.cow.app.Application;
import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.parser.ast.AST;
import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.command.unsafe.RuntimeUnsafe;

public record UnsafeBF(BFBuilder code) implements RuntimeUnsafe {
    @Override
    public void compile(AST ast, BFBuilder builder, ArgumentChain args) {
        Application.debug("Adding BF code: " + code);
        builder.addCode(code);
    }
}
