package com.github.beeflang.cow.parser.ast;

import com.github.beeflang.cow.parser.ast.ctypes.Register;

public interface TypeOfRegister /*extends Translatable<Register>*/ {
    Register translateToRegister(ArgumentChain args);
}
