package de.rccookie.aoc.aoc25;

import java.util.ArrayList;
import java.util.List;

import de.rccookie.aoc.aoc25.util.FastSolution;
import de.rccookie.aoc.aoc25.util.RectPolygon;
import de.rccookie.math.IRect;
import de.rccookie.math.int2;

public class Solution9 extends FastSolution {

    @Override
    public Object task1() {
        List<int2> tiles = new ArrayList<>(vecs());

        long largest = 1;
        for(int2 a : tiles) for(int2 b : tiles) {
            long area = (Math.abs(b.x() - a.x()) + 1L) * (Math.abs(b.y() - a.y()) + 1L);
            largest = Math.max(largest, area);
        }

        return largest;
    }


    @Override
    public Object task2() {

        List<int2> tiles = new ArrayList<>(vecs());
        RectPolygon p = new RectPolygon(tiles);

        long largest = 1;
        IRect rect = IRect.zero();
        for(int2 a : tiles) for(int2 b : tiles) {
            rect.min().set(a);
            rect.max().set(a);
            rect.contain(b);
            if(!p.contains(rect))
                continue;
            long area = (rect.width() + 1L) * (rect.height() + 1L);
            largest = Math.max(largest, area);
        }

        return largest;
    }
}
