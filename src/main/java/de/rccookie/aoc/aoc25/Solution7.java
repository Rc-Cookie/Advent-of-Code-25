package de.rccookie.aoc.aoc25;

import de.rccookie.aoc.aoc25.util.FastSolution;
import de.rccookie.math.Mathf;
import de.rccookie.math.constInt2;

public class Solution7 extends FastSolution {

    long[] beams;
    int splits;

    @Override
    public void load() {
        constInt2 S = grid.find('S');
        beams = new long[grid.width];
        beams[S.x()] = 1;
        splits = 0;

        for(int y=S.y()+1; y<grid.height; y++) {
            long[] newBeams = new long[grid.width];
            for(int x=0; x<grid.width; x++) {
                if(grid.charAt(x,y) == '^') {
                    newBeams[x-1] += beams[x];
                    newBeams[x+1] += beams[x];
                    splits += Mathf.sign(beams[x]);
                }
                else newBeams[x] += beams[x];
            }
            beams = newBeams;
        }
    }

    @Override
    public Object task1() {
        return splits;
    }

    @Override
    public Object task2() {
        return Mathf.sumL(beams);
    }
}
