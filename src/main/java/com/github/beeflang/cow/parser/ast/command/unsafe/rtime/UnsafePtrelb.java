package com.github.beeflang.cow.parser.ast.command.unsafe.rtime;

import com.github.beeflang.cow.app.Application;
import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.parser.ast.AST;
import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.TypeOfAnyAddress;
import com.github.beeflang.cow.parser.ast.command.unsafe.RuntimeUnsafe;

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