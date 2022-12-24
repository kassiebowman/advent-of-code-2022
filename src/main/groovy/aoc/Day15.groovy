package aoc

import aoc.utils.XY

/**
 * Day 15: Beacon Exclusion Zone
 *
 * @see <a href="https://adventofcode.com/2022/day/15">AOC 2022 Day 15</a>
 */
class Day15 {
    long maxX = Long.MIN_VALUE
    long minX = Long.MAX_VALUE
    long maxY = Long.MIN_VALUE
    long minY = Long.MAX_VALUE
    List<Sensor> sensors = []

    int excludeBeacons(String fileName, long y, boolean part1) {
        getClass().getClassLoader().getResource(fileName).splitEachLine("[=,:]") {
            sensors << new Sensor(it[1] as int, it[3] as int, it[5] as int, it[7] as int)
        }

        def closeSensors = sensors.stream()
                .filter(s -> y <= s.xy.y + s.maxDistance && y >= s.xy.y - s.maxDistance)
                .toList()

        long excludedLocations = 0
        for (x in minX..maxX) {
            def currentXY = new XY(x, y)
            // Skip any locations that we know have a beacon
            if (closeSensors.stream().anyMatch(s -> s.beacon == currentXY)) continue

             if (closeSensors.stream().anyMatch(s -> s.getDistance(currentXY) <= s.maxDistance)) {
                 excludedLocations++
             }
        }

        return excludedLocations
    }

    class Sensor {
        final XY xy
        final XY beacon
        final long maxDistance

        Sensor(long x, long y, long beaconX, long beaconY) {
            xy = new XY(x, y)
            beacon = new XY(beaconX, beaconY)
            maxDistance = getDistance(beacon)
            maxX = Math.max(maxX, x + maxDistance)
            minX = Math.min(minX, x - maxDistance)
            maxY = Math.max(maxY, y + maxDistance)
            minY = Math.min(minY, y - maxDistance)
        }

        long getDistance(XY other) {
            return Math.abs(xy.x - other.x) + Math.abs(xy.y - other.y)
        }
    }
}
