package com.github.beeflang.cow.parser.ast.ctypes;

import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.CArgument;
import com.github.beeflang.cow.parser.ast.Translatable;
import com.github.beeflang.cow.parser.ast.TypeOfRegister;

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