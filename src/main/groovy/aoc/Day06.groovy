package aoc

/**
 * Day 6: Tuning Trouble
 *
 * @see <ahref="https://adventofcode.com/2022/day/6">AOC 2022 Day 6</a>
 */
class Day06 {
    String findPacketMarker(String fileName, int markerLength) {

        int initialListSize = markerLength - 1
        int charsReceived = 0
        getClass().getClassLoader().getResource(fileName).eachLine { line ->
            LinkedList marker = []
            line.substring(0,initialListSize).chars().forEach(c -> marker.addLast(c as char))
            charsReceived = initialListSize
            for (i in initialListSize..<line.size()) {
                charsReceived++
                marker.addLast(line[i])
                if (marker.unique(false).size() == marker.size()) break;
                marker.removeFirst()
            }

        }


        return charsReceived
    }
}
