package de.rccookie.aoc.aoc25;

import de.rccookie.aoc.aoc25.util.FastSolution;

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
            int[] batteries = l.chars().map(i -> i - '0').toArray();
            int[] indices = new int[count];
            indices[0] = maxIndex(batteries, 0, batteries.length - indices.length + 1);
            for(int i=1; i<indices.length; i++)
                indices[i] = maxIndex(batteries, indices[i-1] + 1, batteries.length - indices.length + i + 1);
            long x = 0;
            for(int i=0; i<indices.length; i++)
                x = 10 * x + batteries[indices[i]];
            return x;
        });
    }

    private static int maxIndex(int[] arr, int from, int to) {
        if(from >= to)
            return -1;
        int maxIndex = from;
        for(int i=from+1; i<to; i++)
            if(arr[i] > arr[maxIndex])
                maxIndex = i;
        return maxIndex;
    }
}
