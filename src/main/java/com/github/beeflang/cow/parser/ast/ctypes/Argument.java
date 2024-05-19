package com.github.beeflang.cow.parser.ast.ctypes;

import com.github.beeflang.cow.parser.ast.CArgument;

import java.util.List;

public interface Argument extends CArgument {
    int position();

    default CArgument resolve(List<CArgument> args) {
        return args.get(position());
    }
}