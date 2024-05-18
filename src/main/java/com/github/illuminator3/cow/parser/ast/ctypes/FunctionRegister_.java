package com.github.illuminator3.cow.parser.ast.ctypes;

import com.github.illuminator3.cow.app.Application;

public record FunctionRegister_(int position) implements Register {
    @Override
    public AnyAddress translate() {
        return new AnyAddress(position + Application.N_INTERNAL_REGISTERS);
    }
}
