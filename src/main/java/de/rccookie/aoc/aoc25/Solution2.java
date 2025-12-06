package de.rccookie.aoc.aoc25;

import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import de.rccookie.aoc.aoc25.util.FastSolution;

public class Solution2 extends FastSolution {

    @Override
    public Object task1() {
        return sumIDs(s -> s.length() % 2 == 0 && check(s, s.length() / 2));
    }

    @Override
    public Object task2() {
        return sumIDs(s -> IntStream.range(1, s.length() / 2 + 1).anyMatch(i -> check(s,i)));
    }

    private long sumIDs(Predicate<? super String> filter) {
        return split("\\s*,\\s*")
                .map(FastSolution::parseLongs)
                .flatMapToLong(arr -> LongStream.rangeClosed(arr[0], arr[1]))
                .mapToObj(Long::toString)
                .filter(filter)
                .mapToLong(Long::parseLong)
                .sum();
    }

    private boolean check(String s, int subLength) {
        int l = s.length();
        if(l % subLength != 0)
            return false;
        String sub = s.substring(0, subLength);
        for(int i=subLength; i<l; i+=subLength)
            if(!s.startsWith(sub, i))
                return false;
        return true;
    }
}
