package de.rccookie.aoc.aoc25;

import java.util.PriorityQueue;
import java.util.Queue;

import de.rccookie.aoc.aoc25.util.FastSolution;
import de.rccookie.aoc.aoc25.util.IntHeap;
import de.rccookie.aoc.aoc25.util.IntUnionFind;
import de.rccookie.math.int3;
import org.jetbrains.annotations.NotNull;

public class Solution8 extends FastSolution {

    Queue<Pair> pairs;
    IntUnionFind sets;

    @Override
    public void load() {
        int3[] positions = vec3s().toArray(int3[]::new);
        pairs = new PriorityQueue<>();
        for(int i=0; i<positions.length; i++) {
            for(int j=i+1; j<positions.length; j++) {
                int3 a = positions[i], b = positions[j];
                pairs.add(new Pair(i, j, a, b, a.toF().dist2(b.toF())));
            }
        }
        sets = new IntUnionFind(positions.length);
    }

    @Override
    public Object task1() {

        for(int i=0; i<(example?10:1000); i++) {
            Pair p = pairs.remove();
            sets.union(p.i, p.j);
        }

        IntHeap heap = new IntHeap(sets.count());
        for(int c : sets.components())
            heap.enqueue(-sets.size(c));

        return (long) -heap.dequeue() * heap.dequeue() * heap.dequeue();
    }

    @Override
    public Object task2() {
        Pair p;
        do sets.union((p = pairs.remove()).i, p.j);
        while(!sets.connected());
        return (long) p.a.x() * p.b.x();
    }

    record Pair(int i, int j, int3 a, int3 b, double dist) implements Comparable<Pair> {
        @Override
        public int compareTo(@NotNull Solution8.Pair o) {
            return Double.compare(dist, o.dist);
        }
    }
}
