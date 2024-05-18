package com.github.illuminator3.cow.parser.ast.ctypes;

import java.util.Optional;
import java.util.stream.Stream;

public enum CType {
    BFCode("BFCode", true),
    Code("Code"),
    Register("Register"),
    Address("Address"),
    AnyAddress("@AnyAddress", true),
//    Argument("Argument"),
    ;

    private final String name;
    private final boolean internal;

    CType(String name) {
        this(name, false);
    }

    CType(String name, boolean internal) {
        this.name = name;
        this.internal = internal;
    }

    public String getName() {
        return name;
    }

    public boolean isInternal() {
        return internal;
    }

    public static Optional<CType> byName(String name) {
        return Stream.of(values()).filter(ct -> ct.name.equals(name)).findFirst();
    }
}