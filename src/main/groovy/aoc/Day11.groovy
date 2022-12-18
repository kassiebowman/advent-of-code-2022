package aoc

import java.util.function.Function

/**
 * Day 11: Monkey in the Middle
 *
 * @see <a href="https://adventofcode.com/2022/day/11">AOC 2022 Day 11</a>
 */
class Day11 {
    BigInteger playKeepAway(String fileName, boolean part1) {
        List<Monkey> monkeys = []
        def lines = getClass().getClassLoader().getResource(fileName).readLines()

        int numMonkeys = lines.size() / 7 + 1
        for (i in 0..<numMonkeys) {
            def monkey = new Monkey(i)
            monkeys << monkey

            def lineIndex = i * 7 + 1

            def parts = lines[lineIndex++].split(": ")
            if (parts.size() == 2) {
                def items = parts[1].split(", ")
                for (itemIndex in 0..<items.size()) {
                    monkey.items << Long.valueOf(items[itemIndex])
                }
            }

            def operationParams = lines[lineIndex++].split("= ")[1].split(" ")
            def multiply = (operationParams[1] == "*")

            if (operationParams[2] == "old") {
                monkey.operation = old -> multiply ? old * old : old + old
            } else {
                int value = Integer.valueOf(operationParams[2])
                monkey.operation = old -> multiply ? old * value : old + value
            }

            monkey.testValue = lines[lineIndex++].split(" by ")[1] as int
            monkey.trueIndex = lines[lineIndex++].split(" monkey ")[1] as int
            monkey.falseIndex = lines[lineIndex].split(" monkey ")[1] as int
        }

        int numRounds = part1 ? 20 : 10000
        for (round in 1..numRounds) {
//            println("Round: $round")
            for (monkeyIndex in 0..<numMonkeys) {
                Monkey monkey = monkeys[monkeyIndex]

                while (!monkey.items.isEmpty()) {
                    BigInteger item = monkey.items.remove(0)

                    monkey.inspections++
                    item = monkey.operation.apply(item)

                    if (part1) item /= 3

                    def newMonkeyIndex = (item % monkey.testValue == 0) ? monkey.trueIndex : monkey.falseIndex
                    monkeys[newMonkeyIndex].items.add(item)
//                    println("  $newMonkeyIndex <- insp: ${monkeys[newMonkeyIndex].inspections}")
                }
            }
        }

        monkeys = monkeys.sort().reverse()
        return monkeys[0].inspections * monkeys[1].inspections
    }

    class Monkey implements Comparable<Monkey> {
        final int monkeyIndex
        List<BigInteger> items = []
        Function<BigInteger, BigInteger> operation
        int testValue
        int trueIndex
        int falseIndex
        int inspections = 0

        Monkey(int monkeyIndex) {
            this.monkeyIndex = monkeyIndex
        }

        @Override
        int compareTo(Monkey o) {
            if (inspections == o.inspections) return 0
            if (inspections > o.inspections) return 1
            return -1
        }

        @Override
        String toString() {
            return "Monkey " + monkeyIndex + ": " +
//            items.join(", ") +
            "(inspections = " + inspections + ")"
        }
    }
}
