package com.github.illuminator3.cow.parser.ast;

import com.github.illuminator3.cow.parser.ast.ctypes.AnyAddress;

public interface TypeOfAnyAddress /*extends Translatable<AnyAddress>*/ {
    AnyAddress translateToAnyAddress(ArgumentChain args);
}