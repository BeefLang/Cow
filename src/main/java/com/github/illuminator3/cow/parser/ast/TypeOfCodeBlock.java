package com.github.illuminator3.cow.parser.ast;

import com.github.illuminator3.cow.parser.ast.ctypes.CodeBlock;

import java.util.List;

public interface TypeOfCodeBlock /*extends Translatable<CodeBlock>*/ {
    CodeBlock translateToCodeBlock(ArgumentChain args);
}
