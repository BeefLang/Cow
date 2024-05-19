package com.github.illuminator3.cow.parser.ast.ctypes;

public record CInternalRegister(int position) implements Register {
    @Override
    public AnyAddress translate() {
        return new AnyAddress(position);
    }
}
