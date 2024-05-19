package com.github.beeflang.cow.parser.ast.ctypes;

import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.compiler.Compilable;
import com.github.beeflang.cow.parser.ast.AST;
import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.CArgument;
import com.github.beeflang.cow.parser.ast.TypeOfCodeBlock;
import com.github.beeflang.cow.parser.ast.command.Command;

import java.util.List;

public record CodeBlock(List<Command> commands) implements Compilable<ArgumentChain>, CArgument, TypeOfCodeBlock {
    @Override
    public void compile(AST ast, BFBuilder builder, ArgumentChain args) {
        for (Command command : commands) {
            command.compile(ast, builder, args);
        }
    }

    @Override
    public CType getType() {
        return CType.Code;
    }

    @Override
    public CodeBlock translateToCodeBlock(ArgumentChain args) {
        return this;
    }
}