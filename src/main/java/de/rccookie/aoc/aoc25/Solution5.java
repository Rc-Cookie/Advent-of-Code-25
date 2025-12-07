package de.rccookie.aoc.aoc25;

import de.rccookie.aoc.RangedSet;
import de.rccookie.aoc.aoc25.util.FastSolution;

public class Solution5 extends FastSolution {

    private RangedSet ranges() {
        return arrays(0).collect(RangedSet.inclusiveUnion());
    }

    @Override
    public Object task1() {
        return nums(1).filter(ranges()::contains).count();
    }

    @Override
    public Object task2() {
        return ranges().exactSize();
    }
}
