package com.github.illuminator3.cow.parser.ast.command.unsafe.rtime;

import com.github.illuminator3.cow.app.Application;
import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.parser.ast.*;
import com.github.illuminator3.cow.parser.ast.command.unsafe.RuntimeUnsafe;
import com.github.illuminator3.cow.parser.ast.ctypes.AnyAddress;
import com.github.illuminator3.cow.parser.ast.ctypes.CType;

public record UnsafeTranslateAddress(TypeOfAddress address) implements RuntimeUnsafe, CArgument, TypeOfAnyAddress {
    @Override
    public void compile(AST ast, BFBuilder builder, ArgumentChain args) {
        throw new IllegalStateException("Not compilable");
    }

    @Override
    public CType getType() {
        return CType.AnyAddress;
    }

    @Override
    public AnyAddress translateToAnyAddress(ArgumentChain args) {
        Application.debug("address in unsafe: " + address);
        return address.translateToAddress(args.up()).translate();
    }
}
