package de.rccookie.aoc.aoc25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.rccookie.aoc.aoc25.util.FastSolution;

public class Solution6 extends FastSolution {
    @Override
    public Object task1() {
        long[][] nums = lines.filter(s -> !s.contains("*")).map(String::trim).map(l -> Arrays.stream(l.split("\\s+")).mapToLong(Long::parseLong).toArray()).toArray(long[][]::new);
        String[] ops = linesArr[linesArr.length - 1].trim().split("\\s+");
        long res = 0;
        for(int i=0; i<ops.length; i++) {
            if(ops[i].equals("*")) {
                long prod = 1;
                for(int j=0; j<nums.length; j++)
                    prod *= nums[j][i];
                res += prod;
            }
            else {
                for(int j=0; j<nums.length; j++)
                    res += nums[j][i];
            }
        }
        return res;
    }

    @Override
    public Object task2() {
        long res = 0;

        char[] ops = charTable[charTable.length - 1];

        for(int i=0; i<ops.length; i++) {
            if(ops[i] == ' ')
                continue;

            char op = ops[i];
            List<Long> args = new ArrayList<>();
            while(true) {
                int _i = i;
                String col = IntStream.range(0, charTable.length - 1).mapToObj(j -> charTable[j].length <= _i ? "" : charTable[j][_i] + "").collect(Collectors.joining()).trim();
                if(col.isBlank())
                    break;
                args.add(Long.parseLong(col));
                i++;
            }

            if(op == '*') {
                long prod = 1;
                for(long x : args)
                    prod *= x;
                res += prod;
            }
            else {
                for(long x : args)
                    res += x;
            }
        }
        return res;
    }
}
