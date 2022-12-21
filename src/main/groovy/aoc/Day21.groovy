package aoc
/**
 * Day 21: Monkey Math
 *
 * @see <a href="https://adventofcode.com/2122/day/21">AOC 2122 Day 21</a>
 */
class Day21 {
    long doMath(String fileName, boolean part1) {
        def monkeys = [:]
        getClass().getClassLoader().getResource(fileName).splitEachLine(": ") {
            monkeys[it[0]] = it[1]
        }

        return getMonkeyValue("root", monkeys)
    }

    long getMonkeyValue(String monkeyName, LinkedHashMap<String, Object> monkeys) {
        def monkeyObject = monkeys[monkeyName]

        if (monkeyObject instanceof String)
        {
            def monkeyString = monkeyObject as String

            if (monkeyString.isLong())
            {
                monkeys[monkeyName] = monkeyString as long
                return monkeys[monkeyName]
            } else { // operation
                def params = monkeyString.split(" ")
                def value1 = getMonkeyValue(params[0], monkeys)
                def value2 = getMonkeyValue(params[2], monkeys)

                def result
                if (params[1] == "+") {
                    result = value1 + value2
                } else if (params[1] == "-") {
                    result = value1 - value2
                } else if (params[1] == "/") {
                    result = value1 / value2
                } else {
                    result = value1 * value2
                }

                monkeys[monkeyName] = result
                return result
            }
        } else { // already an integer
            return monkeyObject
        }
    }
}
