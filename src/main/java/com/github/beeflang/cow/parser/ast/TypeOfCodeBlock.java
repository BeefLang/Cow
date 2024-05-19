package com.github.beeflang.cow.parser.ast;

import com.github.beeflang.cow.parser.ast.ctypes.CodeBlock;

public interface TypeOfCodeBlock /*extends Translatable<CodeBlock>*/ {
    CodeBlock translateToCodeBlock(ArgumentChain args);
}
