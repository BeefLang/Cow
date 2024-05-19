package com.github.beeflang.cow.parser.ast;

import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.compiler.Compilable;
import com.github.beeflang.cow.parser.ast.command.Command;

import java.util.List;

public record FileAST(List<Command> commands) implements Compilable<Void> {
    @Override
    public void compile(AST ast, BFBuilder builder, Void v) {
        for (Command command : commands) {
            command.compile(ast, builder, new ArgumentChain());
        }
    }
}