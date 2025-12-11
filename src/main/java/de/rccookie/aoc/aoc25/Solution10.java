package de.rccookie.aoc.aoc25;

import java.util.HashMap;
import java.util.Map;

import de.rccookie.aoc.aoc25.util.FastSolution;
import de.rccookie.math.constFloatMxN;
import de.rccookie.math.constIntN;
import de.rccookie.math.floatMxN;
import de.rccookie.math.intN;
import de.rccookie.util.Console;
import de.rccookie.util.text.TableRenderer;

public class Solution10 extends FastSolution {
    @Override
    public Object task1() {
        return sum(l -> {
            String[] parts = l.split("\\s+");
            long target = Long.parseLong(parts[0].substring(1, parts[0].length() - 1).replace('.', '0').replace('#', '1'), 2);
            long[] buttons = new long[parts.length - 2];
            for(int i=0; i<buttons.length; i++)
                for(int b : parseInts(parts[i+1]))
                    buttons[i] |= 1L << (parts[0].length() - b - 3);

            Map<Long, Integer> cache = new HashMap<>();

            return count(target, buttons, 0, cache);
        });
    }

    private int count(long target, long[] buttons, long usedButtons, Map<Long, Integer> cache) {
        if(target == 0)
            return 0;
        long key = target | (usedButtons << 32);
        Integer cached = cache.get(key);
        if(cached != null)
            return cached;

        int count = Integer.MAX_VALUE;
        for(int i=0; i<buttons.length; i++) {
            if((usedButtons & 1L << i) == 0) {
                count = Math.min(count, (int) Math.min(Integer.MAX_VALUE, 1L + count(target ^ buttons[i], buttons, usedButtons | 1L << i, cache)));
            }
        }
        cache.put(key, count);
        return count;
    }
}
