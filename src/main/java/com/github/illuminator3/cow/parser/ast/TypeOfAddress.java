package com.github.illuminator3.cow.parser.ast;

import com.github.illuminator3.cow.parser.ast.ctypes.Address;

import java.util.List;

public interface TypeOfAddress /*extends Translatable<Address>*/ {
    Address translateToAddress(ArgumentChain args);
}
