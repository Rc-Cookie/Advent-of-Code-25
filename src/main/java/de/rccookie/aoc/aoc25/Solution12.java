package de.rccookie.aoc.aoc25;

import java.util.Arrays;

import de.rccookie.aoc.aoc25.util.FastSolution;

public class Solution12 extends FastSolution {
    @Override
    public Object task1() {
        int[] volumes = Arrays.stream(sections).limit(sections.length - 1).mapToInt(s -> (int) s.chars().filter(c -> c == '#').count()).toArray();
        return arrays(sections.length - 1).filter(l -> {
            long size = l[0] * l[1];
            long minRequired = 0;
            for(int i=0; i<volumes.length; i++)
                minRequired += volumes[i] * l[i+2];
            return size >= minRequired;
        }).count();
    }
}
