package aoc
/**
 * Day 10: Cathode-Ray Tube
 *
 * @see <a href="https://adventofcode.com/2022/day/10">AOC 2022 Day 10</a>
 */
class Day10 {
    int findSignalStrength(String fileName, boolean part1) {
        int cycle = 0
        int x = 1
        int cycleOfInterest = 20
        int signalStrength = 0
        StringBuilder sb = new StringBuilder()

        getClass().getClassLoader().getResource(fileName).splitEachLine(" ", params -> {
            def numCycles = 1
            def value = 0
            if (params[0] == "addx")
            {
                numCycles = 2
                value = params[1] as int
            }

            for (i in 1..numCycles) {
                cycle++

                if (cycle == cycleOfInterest)
                {
                    signalStrength += cycle * x
                    cycleOfInterest += 40
                }

                def pixelIndex = (cycle - 1) % 40
                if (pixelIndex == x || pixelIndex == x -1 || pixelIndex == x + 1) {
                    sb.append("#")
                } else {
                    sb.append(".")
                }

                if (cycle % 40 == 0)
                {
                    println(sb.toString())
                    sb = new StringBuilder()
                }
            }

            x += value
        })

        return signalStrength
    }
}
