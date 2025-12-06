package de.rccookie.aoc.aoc25;

import java.util.Arrays;

import de.rccookie.aoc.aoc25.util.FastSolution;
import de.rccookie.aoc.aoc25.util.Streams;

import static de.rccookie.aoc.aoc25.util.Streams.*;

public class Solution6 extends FastSolution {

    private long calc(String[][] nums) {
        return Arrays.stream(nums)
                .gather(zip(lines.getLast().trim().split("\\s+"), (args, op) -> Arrays.stream(args)
                        .map(String::trim)
                        .map(Long::parseLong)
                        .collect(op.equals("*") ? Streams.product() : Streams.sum())
                )).collect(Streams.sum());
    }

    @Override
    public Object task1() {
        return calc(transpose(lines
                .gather(dropLast())
                .map(l -> l.trim().split("\\s+"))
                .toArray(String[][]::new)));
    }

    @Override
    public Object task2() {
        return calc(Arrays.stream(transpose(charTable))
                .map(s -> new String(s, 0, s.length - 1))
                .gather(Streams.split(String::isBlank))
                .map(s -> s.toArray(String[]::new))
                .toArray(String[][]::new));
    }
}
