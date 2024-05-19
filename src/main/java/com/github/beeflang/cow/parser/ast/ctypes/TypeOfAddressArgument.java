package com.github.beeflang.cow.parser.ast.ctypes;

import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.TypeOfAddress;

public record TypeOfAddressArgument(int position) implements Argument, TypeOfAddress {
    @Override
    public CType getType() {
        return CType.Address;
    }

    @Override
    public Address translateToAddress(ArgumentChain args) {
        return ((TypeOfAddress) args.resolve(position)).translateToAddress(args.up());
    }
}
