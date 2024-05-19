package com.github.illuminator3.cow.parser.ast.ctypes;

import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.CArgument;
import com.github.illuminator3.cow.parser.ast.TypeOfAnyAddress;

public record AnyAddress(int position) implements CArgument, TypeOfAnyAddress {
    @Override
    public CType getType() {
        return CType.AnyAddress;
    }

    @Override
    public AnyAddress translateToAnyAddress(ArgumentChain args) {
        return this;
    }
}