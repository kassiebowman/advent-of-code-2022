package aoc

/**
 * Day 4: Camp Cleanup
 *
 * @see <ahref="https://adventofcode.com/2022/day/4">AOC 2022 Day 4</a>
 */
class Day04 {
    int findFullyContainedPairs(String fileName) {
        def fullyContainedPairs = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine(",", pairs -> {
            def section1 = pairs[0].split("-")
            def range1 = ((section1[0] as int)..(section1[1] as int))
            def section2 = pairs[1].split("-")
            def range2 = ((section2[0] as int)..(section2[1] as int))
            def isContained = range1.containsAll(range2) || range2.containsAll(range1)
            fullyContainedPairs += isContained ? 1 : 0

//            println "Range1: $range1, range2: $range2, contained: $isContained"
        })

        return fullyContainedPairs;
    }

    int findOverlappingPairs(String fileName) {
        def overlappingPairs = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine(",", pairs -> {
            def section1 = pairs[0].split("-")
            def range1 = ((section1[0] as int)..(section1[1] as int))
            def section2 = pairs[1].split("-")
            def range2 = ((section2[0] as int)..(section2[1] as int))
            def overlaps = !range1.intersect(range2).isEmpty()
            overlappingPairs += overlaps ? 1 : 0

//            println "Range1: $range1, range2: $range2, overlaps: $overlaps"
        })

        return overlappingPairs;
    }
}
