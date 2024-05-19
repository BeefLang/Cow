package com.github.beeflang.cow.parser.ast.ctypes;

import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.TypeOfCodeBlock;

public record TypeOfCodeBlockArgument(int position) implements Argument, TypeOfCodeBlock {
    @Override
    public CType getType() {
        return CType.Code;
    }

    @Override
    public CodeBlock translateToCodeBlock(ArgumentChain args) {
        return ((TypeOfCodeBlock) args.resolve(position)).translateToCodeBlock(args.up());
    }
}
