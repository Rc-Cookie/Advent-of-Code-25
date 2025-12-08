package de.rccookie.aoc.aoc25.util;

import org.jetbrains.annotations.Contract;

/**
 * A size-based union find implementation where the elements are the integers 0..count-1.
 * Union find is essentially a set of (disjoint) sets (a partition, to be more exact) with
 * limited operations, but high performance on those that exist.
 */
public class IntUnionFind {

    /**
     * For each element, the index of their parent node <b>+1</b>, or 0 if a root node.
     * By shifting all pointers +1, we can use the default value 0 as initial state.
     */
    public final int[] parents;
    /**
     * For root nodes, the size of their subset <b>-1</b>. Alternatively you could see it
     * as the number of descendants. We use -1 because then 0 is again the correct initial
     * value.
     */
    public final int[] sizes;
    /**
     * The number of subsets in this union find.
     */
    public int count;

    /**
     * Creates a new union find with <code>count</code> individual sets, identified by
     * the numbers 0 up to <code>count-1</code>.
     *
     * @param count The number of sets / elements in this union find
     */
    public IntUnionFind(int count) {
        this.count = count;
        this.parents = new int[count];
        this.sizes = new int[count];
    }

    /**
     * @return The number of subsets in this union find.
     */
    @Contract(pure = true)
    public int count() {
        return count;
    }

    /**
     * @return Whether all subsets are connected, i.e. there is only a single set containing
     *         all items.
     */
    @Contract(pure = true)
    public boolean connected() {
        return count == 1;
    }

    /**
     * Returns the (current) canonical representative of the set which contains the given
     * element.
     *
     * @param x The element to get the representative of
     * @return The unique representative of the set containing <code>x</code>
     */
    @Contract(pure = true)
    public int find(int x) {
        int p = parents[x] - 1;
        if(p < 0)
            return x;
        while(true) {
            int pp = parents[p];
            if(pp <= 0)
                return p;
            parents[x] = pp;
            x = p;
            p = pp - 1;
        }
    }

    /**
     * Returns the size of the set containing the given element.
     *
     * @param x The element whose set's size to obtain
     * @return The size of the set containing <code>x</code>
     */
    @Contract(pure = true)
    public int size(int x) {
        return sizes[find(x)] + 1;
    }

    /**
     * Tests whether the two given elements are contained in the same set.
     *
     * @param a The first element
     * @param b The second element
     * @return True iff <code>a</code> and <code>b</code> are in the same set
     */
    @Contract(pure = true)
    public boolean inSameSet(int a, int b) {
        return find(a) == find(b);
    }

    /**
     * Merges the sets containing the two elements into a single larger set, if
     * the elements are in different sets, otherwise does nothing.
     *
     * @param a An element from the first set
     * @param b An element from the second set
     * @return <code>true</code> if <code>a</code> and <code>b</code> were in
     *         different sets (i.e. the sets were merged), <code>false</code>
     *         otherwise
     */
    @Contract(mutates = "this")
    public boolean union(int a, int b) {
        if((a = find(a)) == (b = find(b)))
            return false;
        if(sizes[a] < sizes[b]) {
            parents[a] = b + 1;
            sizes[b] += sizes[a] + 1;
        }
        else {
            parents[b] = a + 1;
            sizes[a] += sizes[b] + 1;
        }
        count--;
        return true;
    }

    /**
     * Collects exactly one representative from each subset in this union find.
     *
     * @return An array with {@link #count()} different elements from pairwise
     *         different sets
     */
    @Contract(pure = true)
    public int[] components() {
        if(count == 1)
            return new int[] { 0 };
        int[] components = new int[count];
        for(int i=0, j=0; i<count; j++)
            if(parents[j] <= 0)
                components[i++] = j;
        return components;
    }
}
