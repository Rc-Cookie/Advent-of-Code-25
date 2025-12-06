package de.rccookie.aoc.aoc25;

import java.util.function.LongBinaryOperator;

import de.rccookie.aoc.aoc25.util.FastSolution;

public class Solution1 extends FastSolution {

    private long simulate(LongBinaryOperator counter) {
        long pos = 50, count = 0;
        for(long offset : lines.map(l -> l.replace('L', '-').replace('R', '+')).map(Long::parseLong)) {
            count += counter.applyAsLong(pos, offset);
            pos = Math.floorMod(pos + offset, 100);
        }
        return count;
    }

    @Override
    public Object task1() {
        return simulate((p,o) -> (p+o) % 100 == 0 ? 1 : 0);
    }

    @Override
    public Object task2() {
        return simulate((p,o) -> (o >= 0 ? p+o : (100-p) % 100 - o) / 100);
    }
}
