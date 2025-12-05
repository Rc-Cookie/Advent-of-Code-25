package de.rccookie.aoc.aoc25;

import java.util.ArrayList;
import java.util.List;

import de.rccookie.aoc.Solution;

public class Solution5 extends Solution {

    private List<long[]> ranges() {
        return input.split("\n\n")[0].lines().map(s -> s.split("-")).map(arr -> new long[] { Long.parseLong(arr[0]), Long.parseLong(arr[1]) }).toList();
    }

    @Override
    public Object task1() {
        List<long[]> ranges = ranges();
        return input.split("\n\n")[1].lines()
                .mapToLong(Long::parseLong)
                .filter(l -> ranges.stream().anyMatch(r -> r[0] <= l && l <= r[1]))
                .count();
    }

    @Override
    public Object task2() {
        List<long[]> ranges = new ArrayList<>();
        for(long[] range : ranges()) {
            List<long[]> now = new ArrayList<>();
            boolean done = false;
            for(long[] r : ranges) {
                if(done || r[1] < range[0])
                    now.add(r);
                else if(r[0] > range[1]) {
                    now.add(range);
                    now.add(r);
                    done = true;
                }
                else {
                    range[0] = Math.min(r[0], range[0]);
                    range[1] = Math.max(r[1], range[1]);
                }
            }
            if(!done)
                now.add(range);
            ranges = now;
        }
        return ranges.stream().mapToLong(r -> r[1] - r[0] + 1).sum();
    }
}
