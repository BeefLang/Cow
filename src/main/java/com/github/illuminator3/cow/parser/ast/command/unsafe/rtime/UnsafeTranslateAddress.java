package com.github.illuminator3.cow.parser.ast.command.unsafe.rtime;

import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.parser.ast.*;
import com.github.illuminator3.cow.parser.ast.command.unsafe.RuntimeUnsafe;
import com.github.illuminator3.cow.parser.ast.ctypes.Address;
import com.github.illuminator3.cow.parser.ast.ctypes.AnyAddress;
import com.github.illuminator3.cow.parser.ast.ctypes.CType;

import java.util.List;

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
        System.out.println("address in unsafe: " + address);
        Address tmp2 = address.translateToAddress(args.up());
        System.out.println("tmp2: " + tmp2);
        return tmp2.translate();
    }
}
