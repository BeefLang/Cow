package com.github.beeflang.cow.parser.ast;

import com.github.beeflang.cow.parser.ast.ctypes.AnyAddress;

public interface TypeOfAnyAddress /*extends Translatable<AnyAddress>*/ {
    AnyAddress translateToAnyAddress(ArgumentChain args);
}