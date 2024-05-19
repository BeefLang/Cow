package com.github.beeflang.cow.parser.ast.ctypes;

import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.CArgument;
import com.github.beeflang.cow.parser.ast.TypeOfAnyAddress;

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