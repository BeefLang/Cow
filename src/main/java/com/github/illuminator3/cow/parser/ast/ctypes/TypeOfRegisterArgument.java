package com.github.illuminator3.cow.parser.ast.ctypes;

import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.CArgument;
import com.github.illuminator3.cow.parser.ast.TypeOfRegister;

import java.util.List;

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
