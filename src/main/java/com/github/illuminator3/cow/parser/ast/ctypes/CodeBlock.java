package com.github.illuminator3.cow.parser.ast.ctypes;

import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.compiler.Compilable;
import com.github.illuminator3.cow.parser.ast.AST;
import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.CArgument;
import com.github.illuminator3.cow.parser.ast.TypeOfCodeBlock;
import com.github.illuminator3.cow.parser.ast.command.Command;

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