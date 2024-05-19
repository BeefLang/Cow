package com.github.beeflang.cow.parser.ast;

import com.github.beeflang.cow.app.Application;
import com.github.beeflang.cow.parser.ast.ctypes.Argument;

import java.util.ArrayList;
import java.util.List;

public class ArgumentChain {
    private List<List<CArgument>> levels = new ArrayList<>();

    public ArgumentChain addLevel(List<CArgument> args) {
        ArgumentChain next = new ArgumentChain();

        next.levels = new ArrayList<>(levels);
        next.levels.add(args);

        return next;
    }

    public ArgumentChain up() {
        ArgumentChain next = new ArgumentChain();

        next.levels = new ArrayList<>(levels);

        if (!next.levels.isEmpty()) {
            next.levels.remove(next.levels.size() - 1);
        }

        return next;
    }

    public CArgument resolve(int position) {
        CArgument next = levels.get(levels.size() - 1).get(position);

        Application.debug("Resolving position: " + position);
        Application.debug("Current chain: " + levels);

        for (int i = 1;; i++) {
            Application.debug("i = " + i);
            Application.debug("Previous: " + next);
            if (next instanceof Argument arg) {
                next = arg.resolve(levels.get(levels.size() - 1 - i));
                Application.debug("Next: " + next);
            } else {
                break;
            }
        }

        return next;
    }

    @Override
    public String toString() {
        return "ArgumentChain{" +
                "levels=" + levels +
                '}';
    }
}