package com.github.beeflang.cow.parser.ast.command.unsafe.rtime;

import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.parser.ast.*;
import com.github.beeflang.cow.parser.ast.command.unsafe.RuntimeUnsafe;
import com.github.beeflang.cow.parser.ast.ctypes.AnyAddress;
import com.github.beeflang.cow.parser.ast.ctypes.CType;

public record UnsafeTranslateRegister(TypeOfRegister register) implements RuntimeUnsafe, CArgument, TypeOfAnyAddress {
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
        return register.translateToRegister(args).translate();
    }
}
