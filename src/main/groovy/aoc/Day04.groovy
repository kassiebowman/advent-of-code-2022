package aoc

/**
 * Day 4: Camp Cleanup
 *
 * @see <a href="https://adventofcode.com/2022/day/4">AOC 2022 Day 4</a>
 */
class Day04 {
    /**
     * Determine how many pairs of section assignments exist where one assignment fully contains the other.
     *
     * @param fileName The name of the file containing the pairs of section assignments, formatted as ##-##,##-##
     * @return The number of section assignment pairs where one pair fully contains the other.
     */
    int findFullyContainedPairs(String fileName) {
        def fullyContainedPairs = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine("[,-]", pairs -> {
            def range1 = ((pairs[0] as int)..(pairs[1] as int))
            def range2 = ((pairs[2] as int)..(pairs[3] as int))
            if(range1.containsAll(range2) || range2.containsAll(range1)) fullyContainedPairs++
//            println "range1: $range1, range2: $range2, fullyContainedPairs: $fullyContainedPairs"
        })

        return fullyContainedPairs
    }

    /**
     * Determine how many pairs of section assignments exist where one assignment overlaps the other.
     *
     * @param fileName The name of the file containing the pairs of section assignments, formatted as ##-##,##-##
     * @return The number of section assignment pairs where one pair overlaps the other.
     */
    int findOverlappingPairs(String fileName) {
        def overlappingPairs = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine("[,-]", pairs -> {
            def range1 = ((pairs[0] as int)..(pairs[1] as int))
            def range2 = ((pairs[2] as int)..(pairs[3] as int))
            if (!range1.intersect(range2).isEmpty()) overlappingPairs++
//            println "range1: $range1, range2: $range2, overlappingPairs: $overlappingPairs"
        })

        return overlappingPairs
    }
}
