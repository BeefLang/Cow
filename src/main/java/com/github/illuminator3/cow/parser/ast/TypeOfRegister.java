package com.github.illuminator3.cow.parser.ast;

import com.github.illuminator3.cow.parser.ast.ctypes.Register;

import java.util.List;

public interface TypeOfRegister /*extends Translatable<Register>*/ {
    Register translateToRegister(ArgumentChain args);
}
