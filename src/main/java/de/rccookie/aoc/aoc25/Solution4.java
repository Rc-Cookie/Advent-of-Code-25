package de.rccookie.aoc.aoc25;

import java.util.ArrayList;
import java.util.List;

import de.rccookie.aoc.aoc25.util.FastSolution;
import de.rccookie.math.constInt2;

public class Solution4 extends FastSolution {

    List<constInt2> getAccessible() {
        List<constInt2> accessible = new ArrayList<>();
        for(constInt2 p : size) {
            if(charAt(p) != '@')
                continue;
            int adj = 0;
            for(constInt2 o : ADJ8)
                if(charAt(p.added(o), ' ') == '@')
                    adj++;
            if(adj < 4)
                accessible.add(p);
        }
        return accessible;
    }

    @Override
    public Object task1() {
        return getAccessible().size();
    }

    @Override
    public Object task2() {
        int total = 0;
        while(true) {
            List<constInt2> accessible = getAccessible();
            if(accessible.isEmpty())
                return total;
            total += accessible.size();
            for(constInt2 p : accessible)
                charTable[p.y()][p.x()] = ' ';
        }
    }
}
