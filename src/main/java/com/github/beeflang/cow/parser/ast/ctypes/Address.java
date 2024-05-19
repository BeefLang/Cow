package com.github.beeflang.cow.parser.ast.ctypes;

import com.github.beeflang.cow.app.Application;
import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.CArgument;
import com.github.beeflang.cow.parser.ast.Translatable;
import com.github.beeflang.cow.parser.ast.TypeOfAddress;

public record Address(int position) implements Translatable<AnyAddress>, CArgument, TypeOfAddress {
    @Override
    public AnyAddress translate() {
        return new AnyAddress(position * Application.MEMORY_SPACING + Application.N_INTERNAL_REGISTERS + Application.N_FUNCTION_REGISTERS);
    }

    @Override
    public CType getType() {
        return CType.Address;
    }

    @Override
    public Address translateToAddress(ArgumentChain args) {
        return this;
    }
}