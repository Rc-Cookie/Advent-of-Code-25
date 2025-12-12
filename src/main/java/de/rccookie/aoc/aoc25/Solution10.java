package de.rccookie.aoc.aoc25;

import de.rccookie.aoc.aoc25.util.FastSolution;
import de.rccookie.aoc.aoc25.util.IntArrayList;

public class Solution10 extends FastSolution {

    @Override
    public Object task1() {
        return sum(l -> {
            String[] parts = l.split("\\s+");
            int target = Integer.parseInt(parts[0].substring(1, parts[0].length() - 1).replace('.', '0').replace('#', '1'), 2);
            int[] buttons = new int[parts.length - 2];
            for(int i=0; i<buttons.length; i++)
                for(int b : parseInts(parts[i+1]))
                    buttons[i] |= 1 << (parts[0].length() - b - 3);

            int[] combinations = combinations(target, buttons);
            int min = Integer.MAX_VALUE;
            for(int c : combinations)
                min = Math.min(min, Integer.bitCount(c));
            return min;
        });
    }

    /**
     * Constructs all combinations of pressing a button 1 or 0 times to achieve the given
     * target mask, with toggling (i.e. pressing twice turns the state back off).
     */
    private static int[] combinations(int target, int[] buttons) {
        IntArrayList combinations = new IntArrayList();
        if(target == 0)
            combinations.add(0);
        // Use Gray Code iteration order; that way we only need a single update per iteration
        // rather than reconstructing the whole result pattern each time
        int end = 1 << buttons.length;
        int c = 0, pat = 0;
        for(int i=1; i<end; i++) {
            int l = Integer.lowestOneBit(i);
            c ^= l;
            pat ^= buttons[Integer.numberOfTrailingZeros(l)];
            if(pat == target)
                combinations.add(c);
        }
        return combinations.toArrayNoCopy();
    }

    /**
     * Returns in the n-th bit a 1 iff the n-th value is an odd number.
     */
    private static int parity(int[] values) {
        assert values.length <= 32;
        int par = 0;
        for(int i=0; i<values.length; i++)
            par |= (values[i] & 1) << i;
        return par;
    }

    @Override
    public Object task2() {
        return sum(l -> {
            String[] parts = l.split("\\s+");

            int[][] buttons = new int[parts.length - 2][];
            int[] buttonMasks = new int[parts.length - 2];
            for(int i=0; i<buttonMasks.length; i++)
                for(int b : buttons[i] = parseInts(parts[i+1]))
                    buttonMasks[i] |= 1 << b;


            int[] target = parseInts(parts[parts.length - 1]);

            return count(target, buttonMasks, buttons, new int[1 << target.length][]);
        });
    }

    /**
     * Returns the minimum number of button presses needed to achieve the specified target
     * values by pressing any combination of the given buttons.
     *
     * @param target The target values
     * @param buttonMasks A bitmask per button, where the i-th bit indicates whether the button
     *                    increases the i-th value
     * @param buttons The same buttons, but for each button, a list of indices whose value this
     *                button increases
     * @param combinationsCache Cache for {@link #combinations(int, int[])}, where the i-th
     *                          element is <code>combinations(i, buttonMasks)</code>, or null
     *                          if not yet populated
     * @return The minimum number of button presses needed to achieve the given pattern
     * @implNote Based on the algorithm proposed <a href="https://www.reddit.com/r/adventofcode/comments/1pk87hl/2025_day_10_part_2_bifurcate_your_way_to_victory/">here</a>
     */
    private static int count(int[] target, int[] buttonMasks, int[][] buttons, int[][] combinationsCache) {

        int parity = parity(target);

        int[] combinations = combinationsCache[parity];
        if(combinations == null)
            combinations = combinationsCache[parity] = combinations(parity, buttonMasks);

        int[] remaining = new int[target.length];

        int min = Integer.MAX_VALUE;
        outer: for(int c : combinations) {
            int count = Integer.bitCount(c);
            System.arraycopy(target, 0, remaining, 0, target.length);

            for(int i=0; i<buttonMasks.length; i++)
                if((c & 1 << i) != 0 && !pressButton(buttons[i], remaining))
                    continue outer;

            boolean zero = true;
            for(int i=0; i<remaining.length; i++)
                zero &= (remaining[i] >>>= 1) == 0;

            int additional = zero ? 0 : count(remaining, buttonMasks, buttons, combinationsCache);
            if(additional < Integer.MAX_VALUE && count + (additional << 1) < min)
                min = count + (additional << 1);
        }
        return min;
    }

    /**
     * Decreases all values controlled by the given button by 1.
     *
     * @return <code>false</code> if any value becomes negative from pressing
     *         the button, <code>true</code> otherwise
     */
    private static boolean pressButton(int[] button, int[] remaining) {
        for(int b : button)
            if(--remaining[b] < 0)
                return false;
        return true;
    }
}
