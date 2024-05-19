package com.github.illuminator3.cow.parser.ast.command.unsafe.rtime;

import com.github.illuminator3.cow.app.Application;
import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.parser.ast.AST;
import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.TypeOfAnyAddress;
import com.github.illuminator3.cow.parser.ast.command.unsafe.RuntimeUnsafe;
import com.github.illuminator3.cow.parser.ast.ctypes.AnyAddress;

public record UnsafePtrelt(TypeOfAnyAddress address) implements RuntimeUnsafe {
    @Override
    public void compile(AST ast, BFBuilder builder, ArgumentChain arguments) {
        Application.debug("address: " + address);
        AnyAddress tmp = address.translateToAnyAddress(arguments);
        Application.debug("translated any address: " + tmp);
        Application.debug("tmp position: " + tmp.position() + " app zero pos: " + Application.ZERO_POSITION);

        int pos = tmp.position();

        if (pos < Application.ZERO_POSITION) {
            for (int i = 0; i < Application.ZERO_POSITION - pos; i++) {
                builder.left();
            }
        } else {
            for (int i = 0; i < pos - Application.ZERO_POSITION; i++) {
                builder.right();
            }
        }
    }
}
