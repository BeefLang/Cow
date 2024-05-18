package com.github.illuminator3.cow.parser.ast.command.unsafe.rtime;

import com.github.illuminator3.cow.compiler.BFBuilder;
import com.github.illuminator3.cow.parser.ast.AST;
import com.github.illuminator3.cow.parser.ast.ArgumentChain;
import com.github.illuminator3.cow.parser.ast.CArgument;
import com.github.illuminator3.cow.parser.ast.TypeOfCodeBlock;
import com.github.illuminator3.cow.parser.ast.command.Command;
import com.github.illuminator3.cow.parser.ast.command.unsafe.RuntimeUnsafe;
import com.github.illuminator3.cow.parser.ast.ctypes.Argument;
import com.github.illuminator3.cow.parser.ast.ctypes.CodeBlock;

import java.util.List;

public record UnsafeSub(TypeOfCodeBlock code) implements RuntimeUnsafe {
    @Override
    public void compile(AST ast, BFBuilder builder, ArgumentChain args) {
        System.out.println("code: " + code);
        System.out.println("current argument chain: " + args);
        for (Command command : code.translateToCodeBlock(args).commands()) {
            System.out.println("command: " + command);
            command.compile(ast, builder, args.up());
        }
    }
}
