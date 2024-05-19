package com.github.beeflang.cow.parser.ast.command.unsafe.rtime;

import com.github.beeflang.cow.app.Application;
import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.parser.ast.AST;
import com.github.beeflang.cow.parser.ast.ArgumentChain;
import com.github.beeflang.cow.parser.ast.TypeOfCodeBlock;
import com.github.beeflang.cow.parser.ast.command.Command;
import com.github.beeflang.cow.parser.ast.command.unsafe.RuntimeUnsafe;

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
