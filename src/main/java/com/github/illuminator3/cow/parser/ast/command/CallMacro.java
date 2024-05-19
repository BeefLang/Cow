package com.github.illuminator3.cow.parser.ast.command;

import com.github.illuminator3.cow.app.Application;
import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.parser.ast.AST;
import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.CArgument;
import com.github.illuminator3.cow.parser.ast.Macro;

import java.util.List;

public record CallMacro(Macro macro, List<CArgument> arguments) implements Command {
    @Override
    public void compile(AST ast, BFBuilder builder, ArgumentChain args) {
        Application.debug("in call macro: " + macro.name());
        macro.compile(ast, builder, args.addLevel(arguments));
    }

    @Override
    public boolean isInternal() {
        return macro.internal();
    }
}