package de.rccookie.aoc.aoc25;

import de.rccookie.aoc.RangedSet;
import de.rccookie.aoc.aoc25.util.FastSolution;

public class Solution5 extends FastSolution {

    private RangedSet ranges() {
        return input.split("\n\n")[0].lines()
                .map(s -> s.replace('-', ','))
                .map(FastSolution::parseLongs)
                .collect(RangedSet.inclusiveUnion());
    }

    @Override
    public Object task1() {
        return input.split("\n\n")[1].lines()
                .mapToLong(Long::parseLong)
                .filter(r -> ranges().contains(r))
                .count();
    }

    @Override
    public Object task2() {
        return ranges().exactSize();
    }
}
