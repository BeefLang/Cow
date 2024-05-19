package com.github.illuminator3.cow.parser.ast.ctypes;

import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.CArgument;
import com.github.illuminator3.cow.parser.ast.Translatable;
import com.github.illuminator3.cow.parser.ast.TypeOfRegister;

public interface Register extends Translatable<AnyAddress>, CArgument, TypeOfRegister {
    int position();

    @Override
    default CType getType() {
        return CType.Register;
    }

    @Override
    default Register translateToRegister(ArgumentChain args) {
        return this;
    }
}