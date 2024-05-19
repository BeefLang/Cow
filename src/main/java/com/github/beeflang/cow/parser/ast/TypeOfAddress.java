package com.github.beeflang.cow.parser.ast;

import com.github.beeflang.cow.parser.ast.ctypes.Address;

public interface TypeOfAddress /*extends Translatable<Address>*/ {
    Address translateToAddress(ArgumentChain args);
}
