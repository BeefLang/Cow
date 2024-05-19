package com.github.beeflang.cow.parser.ast.ctypes;

import com.github.beeflang.cow.app.Application;

// if named FunctionRegister it won't compile for whatever reason... same thing applies to InternalRegister
public record CFunctionRegister(int position) implements Register {
    @Override
    public AnyAddress translate() {
        return new AnyAddress(position + Application.N_INTERNAL_REGISTERS);
    }
}
