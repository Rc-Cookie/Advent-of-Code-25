package de.rccookie.aoc.aoc25.util;

import java.util.List;

import de.rccookie.math.Mathf;
import de.rccookie.math.constIRect;
import de.rccookie.math.constInt2;

public class RectPolygon {

    private static final int SCALE = 4;

    private final int[] borders;
    private final boolean startsHorizontal;

    public RectPolygon(List<? extends constInt2> vertices) {

        int size = vertices.size();

        int[] xs = new int[size], ys = new int[size];
        int n = 0;

        for(int i=0; i<size; i++) {
            constInt2 a = vertices.get((i+size-1) % size);
            constInt2 b = vertices.get(i);
            constInt2 c = vertices.get((i+1) % size);

            int bx = b.x(), by = b.y();
            int abx = bx - a.x(), aby = by - a.y();
            int bcx = c.x() - bx, bcy = c.y() - by;

            int t = Mathf.sign((long) abx * bcy - (long) aby * bcx);
            if(t == 0)
                continue;

            int dx = Mathf.sign(abx) - Mathf.sign(bcx);
            int dy = Mathf.sign(aby) - Mathf.sign(bcy);
            xs[n] = SCALE * bx + dx * t;
            ys[n] = SCALE * by + dy * t;
            n++;
        }

        borders = new int[n * 4];
        for(int i=0; i<n; i++) {
            int j = (i+1) % n;

            borders[4 * i] = Math.min(xs[i], xs[j]);
            borders[4 * i + 1] = Math.min(ys[i], ys[j]);
            borders[4 * i + 2] = Math.max(xs[i], xs[j]);
            borders[4 * i + 3] = Math.max(ys[i], ys[j]);
        }

        startsHorizontal = borders[1] == borders[3];
    }

    public boolean contains(constIRect rect) {
        return contains(rect.min().x(), rect.min().y(), rect.max().x(), rect.max().y());
    }

    public boolean contains(int minX, int minY, int maxX, int maxY) {
        minX *= SCALE;
        minY *= SCALE;
        maxX *= SCALE;
        maxY *= SCALE;
        for(int i = 0; i < borders.length; i+=4)
            if(borders[i] <= maxX && minX <= borders[i + 2] && borders[i + 1] <= maxY && minY <= borders[i + 3])
                return false;
        return true;
    }

    public boolean contains(constInt2 p) {
        for(int i = (startsHorizontal?4:0); i < borders.length; i+=8)
            if(borders[i + 1] <= p.x() && p.x() <= borders[i + 3] && borders[i] <= p.x())
                return false;
        return true;
    }
}
