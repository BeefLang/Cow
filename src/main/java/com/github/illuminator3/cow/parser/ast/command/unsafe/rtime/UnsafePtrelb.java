package com.github.illuminator3.cow.parser.ast.command.unsafe.rtime;

import com.github.illuminator3.cow.app.Application;
import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.parser.ast.AST;
import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.TypeOfAnyAddress;
import com.github.illuminator3.cow.parser.ast.command.unsafe.RuntimeUnsafe;

public record UnsafePtrelb(TypeOfAnyAddress address) implements RuntimeUnsafe {
    @Override
    public void compile(AST ast, BFBuilder builder, ArgumentChain arguments) {
        int pos = address.translateToAnyAddress(arguments).position();

        if (pos < Application.ZERO_POSITION) {
            for (int i = 0; i < Application.ZERO_POSITION - pos; i++) {
                builder.right();
            }
        } else {
            for (int i = 0; i < pos - Application.ZERO_POSITION; i++) {
                builder.left();
            }
        }
    }
}