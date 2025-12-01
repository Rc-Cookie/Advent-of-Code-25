package de.rccookie.aoc.aoc25;

import de.rccookie.aoc.aoc25.util.FastSolution;

public class Solution1 extends FastSolution {

    private long parse(String line) {
        return (line.startsWith("L") ? -1 : 1) * Long.parseLong(line.substring(1));
    }

    @Override
    public Object task1() {
        long count = 0;
        long pos = 50;
        for(long offset : lines.stream().map(this::parse)) {
            pos = Math.floorMod(pos + offset, 100);
            if(pos == 0)
                count++;
        }
        return count;
    }

    @Override
    public Object task2() {
        long count = 0;
        long pos = 50;
        for(long offset : lines.stream().map(this::parse)) {
            if(offset >= 0)
                count += (pos + offset) / 100;
            else count += ((100 - pos) % 100 - offset) / 100;
            pos = Math.floorMod(pos + offset, 100);
        }
        return count;
    }
}
