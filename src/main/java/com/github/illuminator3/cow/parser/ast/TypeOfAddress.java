package com.github.illuminator3.cow.parser.ast;

import com.github.illuminator3.cow.parser.ast.ctypes.Address;

public interface TypeOfAddress /*extends Translatable<Address>*/ {
    Address translateToAddress(ArgumentChain args);
}
