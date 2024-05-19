package com.github.beeflang.cow.parser.ast.ctypes;

import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.TypeOfAnyAddress;

public record TypeOfAnyAddressArgument(int position) implements Argument, TypeOfAnyAddress {
    @Override
    public CType getType() {
        return CType.AnyAddress;
    }

    @Override
    public AnyAddress translateToAnyAddress(ArgumentChain args) {
        return ((TypeOfAnyAddress) args.resolve(position)).translateToAnyAddress(args.up());
    }
}
