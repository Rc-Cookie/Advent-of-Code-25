# Advent of Code 2025

My solutions to the [Advent of Code](https://adventofcode.com) puzzles of 2025.

I am using my [Advent-of-Code-Runner](https://github.com/Rc-Cookie/advent-of-code-runner) library
which automatically downloads input, submits output and generally simplifies development with some
commonly used helper functions. Feel free to check it out yourself if you are using Java!

## Performance

While I didn't spent particularly much time on extreme optimization this year (except [Day 10 Part 2](src/main/java/de/rccookie/aoc/aoc25/Solution10.java)), all puzzles still complete in a total of just over a half a second:
```
┌────────╥──────────┬───────────┐
│        ║   Task 1 │    Task 2 │
╞════════╬══════════╪═══════════╡
│  Day 1 ║  7.217ms │   3.102ms │
├────────╫──────────┼───────────┤
│  Day 2 ║ 94.621ms │ 244.037ms │
├────────╫──────────┼───────────┤
│  Day 3 ║  1.967ms │   0.421ms │
├────────╫──────────┼───────────┤
│  Day 4 ║  9.847ms │  60.425ms │
├────────╫──────────┼───────────┤
│  Day 5 ║  5.568ms │   0.300ms │
├────────╫──────────┼───────────┤
│  Day 6 ║  9.249ms │   5.975ms │
├────────╫──────────┼───────────┤
│  Day 7 ║  0.005ms │   0.010ms │
├────────╫──────────┼───────────┤
│  Day 8 ║  2.477ms │   7.785ms │
├────────╫──────────┼───────────┤
│  Day 9 ║ 12.856ms │  43.320ms │
├────────╫──────────┼───────────┤
│ Day 10 ║  7.027ms │  44.106ms │
├────────╫──────────┼───────────┤
│ Day 11 ║  3.497ms │   1.291ms │
├────────╫──────────┼───────────┤
│ Day 12 ║  3.127ms │       N/A │
└────────╨──────────┴───────────┘
Total: 568.229318ms
```
As always, this excludes JVM startup and loading the input file (but these times are for a single pass only. With 100 iterations the average is already down to 260ms).
