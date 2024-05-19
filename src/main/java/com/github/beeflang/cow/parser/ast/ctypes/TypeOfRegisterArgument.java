package com.github.beeflang.cow.parser.ast.ctypes;

import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.TypeOfRegister;

public record TypeOfRegisterArgument(int position) implements Argument, TypeOfRegister {
    @Override
    public CType getType() {
        return CType.Register;
    }

    @Override
    public Register translateToRegister(ArgumentChain args) {
        return ((TypeOfRegister) args.resolve(position)).translateToRegister(args.up());
    }
}
