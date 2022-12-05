package aoc

/**
 * Day 5: Supply Stacks
 *
 * @see <ahref="https://adventofcode.com/2022/day/5"  >  AOC 2022 Day 5</a>
 */
class Day05 {
    String findTopCrates(String fileName, boolean moveTogether) {
        def buildingMap = true
        List<Deque<Character>> stacks = []
        getClass().getClassLoader().getResource(fileName).eachLine { line ->
            {
                if (!line) {
                    // Done with initial map; move to rearrangement
//                    println stacks
                    buildingMap = false
                } else if (buildingMap) {
                    if (line[1].isNumber()) return;

                    for (int i = 1, stackIndex = 0; i < line.length(); i += 4, stackIndex++) {
                        if (stacks[stackIndex] == null) stacks[stackIndex] = new LinkedList<Character>()
                        if (line[i] != " ") stacks[stackIndex].addFirst(line[i])
                    }
                } else {
                    def params = line.split("move | from | to ")
                    def cratesMoved = params[1] as int
                    def startStack = params[2] as int
                    def endStack = params[3] as int

//                    println "cratesMoved: $cratesMoved, startStack: $startStack, endStack: $endStack"

                    def startIndex = startStack - 1
                    def endIndex = endStack - 1
                    def stacksToMove = [] as LinkedList
                    for (i in 0..<cratesMoved) {
                        stacksToMove << stacks[startIndex].removeLast()
                    }

                    if (moveTogether) stacksToMove = stacksToMove.reverse()
                    stacksToMove.forEach(s -> stacks[endIndex].addLast(s))
                }
            }
        }

        List<Character> topCrates = []
        stacks.forEach(s -> {
            if (s.last) topCrates << s.last
        })

        return topCrates.join("")
    }
}
