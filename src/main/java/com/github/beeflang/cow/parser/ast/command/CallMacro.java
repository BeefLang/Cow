package com.github.beeflang.cow.parser.ast.command;

import com.github.beeflang.cow.app.Application;
import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.parser.ast.AST;
import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.CArgument;
import com.github.beeflang.cow.parser.ast.Macro;

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