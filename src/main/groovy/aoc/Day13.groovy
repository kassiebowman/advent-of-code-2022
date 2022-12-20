package aoc

import groovy.json.JsonSlurper

/**
 * Day 13: Distress Signal
 *
 * @see <a href="https://adventofcode.com/2022/day/13">AOC 2022 Day 13</a>
 */
class Day13 {
    int orderPackets(String fileName, boolean part1) {
        def indexSum = 0
        def pairIndex = 1
        def jsonSlurper = new JsonSlurper()
        def leftPacket = null
        def packets = []
        getClass().getClassLoader().getResource(fileName).eachLine {
            if (it == "") {
                leftPacket = null
                pairIndex++
            } else {
                packets << jsonSlurper.parseText(it)

                if (leftPacket == null) {
                    leftPacket = packets.last()
                } else {
                    def result = compare(leftPacket, packets.last())
                    if (result < 0) indexSum += pairIndex
                }
            }
        }

        if (part1) return indexSum

        def dividers = ["[[2]]", "[[6]]"]
        def dividerPackets = dividers.stream().map({ jsonSlurper.parseText(it) }).toList()
        packets.addAll(dividerPackets)

        packets.sort((left, right) -> compare(left, right))

        return dividerPackets.stream()
                .mapToInt(d -> packets.findIndexOf({ it == d }) + 1)
                .reduce((a, b) -> a * b).orElse(-1)
    }

    int compare(Object left, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return Integer.compare(left as int, right as int)
        }

        def leftList
        def rightList
        if (left instanceof List && right instanceof List) {
            leftList = left as List
            rightList = right as List
        } else { // one integer; one list
            if (left instanceof List) {
                leftList = left as List
                rightList = [right as int]
            } else {
                leftList = [left as int]
                rightList = right as List
            }
        }

        def leftSize = leftList.size()
        def rightSize = rightList.size()
        for (i in 0..<leftSize) {
            if (rightSize <= i) return 1

            def result = compare(leftList[i], rightList[i])
            if (result != 0) return result
        }

        return Integer.compare(leftSize, rightSize)
    }
}
