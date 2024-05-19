package com.github.beeflang.cow.parser.ast;

import com.github.beeflang.cow.app.Application;
import com.github.beeflang.cow.compiler.BFBuilder;
import com.github.beeflang.cow.compiler.Compilable;
import com.github.beeflang.cow.parser.ast.command.Command;
import com.github.beeflang.cow.parser.ast.ctypes.CType;

import java.util.List;
import java.util.Objects;

public final class Macro implements Compilable<ArgumentChain> {
    private final String name;
    private final List<CType> parameters;
    private List<Command> commands;
    private final boolean internal;

    public Macro(String name, List<CType> parameters, List<Command> commands, boolean internal) {
        this.name = name;
        this.parameters = parameters;
        this.commands = commands;
        this.internal = internal;
    }

    @Override
    public void compile(AST ast, BFBuilder builder, ArgumentChain arguments) {
        Application.debug("in macro: " + name);
        Application.debug(arguments);
        for (Command command : commands) {
            Application.debug("compiling command: " + command);
            command.compile(ast, builder, arguments);
            Application.debug("done compiling command");
        }
    }

    public void updateCommands(List<Command> news) {
        this.commands = news;
    }

    public String name() {
        return name;
    }

    public List<CType> parameters() {
        return parameters;
    }

    public List<Command> commands() {
        return commands;
    }

    public boolean internal() {
        return internal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Macro) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.parameters, that.parameters) &&
                Objects.equals(this.commands, that.commands) &&
                this.internal == that.internal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parameters, commands, internal);
    }

    @Override
    public String toString() {
        return "Macro[" +
                "name=" + name + ", " +
                "parameters=" + parameters + ", " +
                "commands=" + commands + ", " +
                "internal=" + internal + ']';
    }

}