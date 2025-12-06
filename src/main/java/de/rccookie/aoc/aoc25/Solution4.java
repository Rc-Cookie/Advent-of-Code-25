package de.rccookie.aoc.aoc25;

import java.util.List;

import de.rccookie.aoc.aoc25.util.FastSolution;
import de.rccookie.math.int2;

public class Solution4 extends FastSolution {

    List<int2> accessible() {
        return grid.stream('@').filter(p -> grid.adjPos8(p, '@').length < 4);
    }

    @Override
    public Object task1() {
        return accessible().size();
    }

    @Override
    public Object task2() {
        int change, total = 0;
        do total += change = grid.set(accessible(), '.');
        while(change != 0);
        return total;
    }
}
