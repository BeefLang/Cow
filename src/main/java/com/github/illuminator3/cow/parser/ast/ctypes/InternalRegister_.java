package com.github.illuminator3.cow.parser.ast.ctypes;

import com.github.illuminator3.cow.parser.ast.CArgument;

import java.util.List;

public record InternalRegister_(int position) implements Register {
    @Override
    public AnyAddress translate() {
        return new AnyAddress(position);
    }
}
