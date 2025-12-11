package de.rccookie.aoc.aoc25;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.rccookie.aoc.aoc25.util.FastSolution;
import de.rccookie.graph.SimpleGraph;
import de.rccookie.graph.SimpleHashGraph;

public class Solution11 extends FastSolution {

    SimpleGraph<String> inverse;

    @Override
    public void load() {
        inverse = new SimpleHashGraph<>();
        for(String l : lines)
            inverse.connectFromAll(Arrays.asList(l.substring(5).split("\\s+")), l.substring(0,3), 1);
    }

    @Override
    public Object task1() {
        return count("you", "out");
    }

    @Override
    public Object task2() {
        return count("svr", "dac", "fft", "out") +
               count("svr", "fft", "dac", "out");
    }

    private long count(String... path) {
        long count = 1;
        for(int i = 1; i < path.length && count != 0; i++) {
            Map<String, Long> cache = new HashMap<>();
            cache.put(path[i-1], 1L);
            count *= count(path[i], cache);
        }
        return count;
    }

    private long count(String dst, Map<String, Long> cache) {
        Long c = cache.get(dst);
        if(c == null)
            cache.put(dst, c = inverse.adj(dst).keySet().stream().mapToLong(src -> count(src, cache)).sum());
        return c;
    }
}
