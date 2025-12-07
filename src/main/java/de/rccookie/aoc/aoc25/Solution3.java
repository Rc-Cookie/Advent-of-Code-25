package de.rccookie.aoc.aoc25;

import de.rccookie.aoc.aoc25.util.FastSolution;
import de.rccookie.math.Mathf;

public class Solution3 extends FastSolution {

    @Override
    public Object task1() {
        return maxJoltage(2);
    }

    @Override
    public Object task2() {
        return maxJoltage(12);
    }

    private long maxJoltage(int count) {
        return sum(l -> {
            int[] batteries = digits(l);
            int[] indices = new int[count];
            indices[0] = Mathf.maxIndex(batteries, 0, batteries.length - indices.length + 1);
            for(int i=1; i<indices.length; i++)
                indices[i] = Mathf.maxIndex(batteries, indices[i-1] + 1, batteries.length - indices.length + i + 1);
            long x = 0;
            for(int i=0; i<indices.length; i++)
                x = 10 * x + batteries[indices[i]];
            return x;
        });
    }
}
