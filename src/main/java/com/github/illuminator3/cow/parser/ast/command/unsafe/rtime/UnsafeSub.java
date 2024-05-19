package com.github.illuminator3.cow.parser.ast.command.unsafe.rtime;

import com.github.illuminator3.cow.app.Application;
import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.parser.ast.AST;
import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.TypeOfCodeBlock;
import com.github.illuminator3.cow.parser.ast.command.Command;
import com.github.illuminator3.cow.parser.ast.command.unsafe.RuntimeUnsafe;

public record UnsafeSub(TypeOfCodeBlock code) implements RuntimeUnsafe {
    @Override
    public void compile(AST ast, BFBuilder builder, ArgumentChain args) {
        Application.debug("code: " + code);
        Application.debug("current argument chain: " + args);
        for (Command command : code.translateToCodeBlock(args).commands()) {
            Application.debug("command: " + command);
            command.compile(ast, builder, args.up());
        }
    }
}
