package com.github.illuminator3.cow.compiler;

import com.github.illuminator3.cow.parser.ast.CArgument;
import com.github.illuminator3.cow.parser.ast.ctypes.CType;

public class BFBuilder implements CArgument {
    public static BFBuilder empty() {
        return new BFBuilder();
    }

    public static BFBuilder code(String code) {
        return empty().addCode(code);
    }

    private StringBuilder code = new StringBuilder();

    public BFBuilder addCode(String code) {
        if (!code.matches("^[+-<>\\[\\]]*$")) {
            throw new IllegalArgumentException();
        }

        this.code.append(code);

        return this;
    }

    public BFBuilder addCode(BFBuilder code) {
        return this.addCode(code.code.toString());
    }

    public BFBuilder right() {
        return this.addCode(">");
    }

    public BFBuilder left() {
        return this.addCode("<");
    }

    public BFBuilder add() {
        return this.addCode("+");
    }

    public BFBuilder sub() {
        return this.addCode("-");
    }

    public BFBuilder loopBegin() {
        return this.addCode("[");
    }

    public BFBuilder loopEnd() {
        return this.addCode("]");
    }

    public BFBuilder loop(String code) {
        if (!code.matches("^[+-<>\\[\\]]$")) {
            throw new IllegalArgumentException();
        }

        this.loopBegin();
        this.code.append(code);
        this.loopEnd();

        return this;
    }

    public BFBuilder loop(BFBuilder code) {
        return this.loop(code.code.toString());
    }

    public void verify() throws IllegalStateException {
        if (code.chars().filter(c -> c == '[').count() != code.chars().filter(c -> c == '[').count()) {
            throw new IllegalStateException("BF Code loop imbalance");
        }
    }

    @Override
    public String toString() {
        return this.code.toString();
    }

    @Override
    public CType getType() {
        return CType.BFCode;
    }
}