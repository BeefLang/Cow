package com.github.illuminator3.cow.parser.ast.ctypes;

import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.CArgument;
import com.github.illuminator3.cow.parser.ast.TypeOfAnyAddress;

import java.util.List;

public record TypeOfAnyAddressArgument(int position) implements Argument, TypeOfAnyAddress {
    @Override
    public CType getType() {
        return CType.AnyAddress;
    }

    @Override
    public AnyAddress translateToAnyAddress(ArgumentChain args) {
        TypeOfAnyAddress tmp = (TypeOfAnyAddress) args.resolve(position);
        System.out.println("tmp: " + tmp);
        return tmp.translateToAnyAddress(args.up());
    }
}
