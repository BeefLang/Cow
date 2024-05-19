package com.github.beeflang.cow.parser.ast.ctypes;

public record CInternalRegister(int position) implements Register {
    @Override
    public AnyAddress translate() {
        return new AnyAddress(position);
    }
}
