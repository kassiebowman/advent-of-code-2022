package aoc

import aoc.utils.XY

/**
 * Day 14: Regolith Reservoir
 *
 * @see <a href="https://adventofcode.com/2022/day/14">AOC 2022 Day 14</a>
 */
class Day14 {
    int fillWithSand(String fileName, boolean part1) {
        def paths = []
        def pathIndex = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine(" -> ", coords -> {
            paths[pathIndex] = []
            coords.forEach({
                def values = it.split(",",)
                paths[pathIndex] << new XY(values[0] as int, values[1] as int)
            })
            pathIndex++
        })

        def minX = Integer.MAX_VALUE
        def maxX = Integer.MIN_VALUE
        def minY = Integer.MAX_VALUE
        def maxY = Integer.MIN_VALUE
        paths.stream().flatMap(path -> path.stream()).map(xy -> xy as XY).forEach(xy -> {
            if (xy.x < minX) minX = xy.x
            if (xy.x > maxX) maxX = xy.x
            if (xy.y < minY) minY = xy.y
            if (xy.y > maxY) maxY = xy.y
        })

//        println "MinX: $minX, MaxX: $maxX, MinY: $minY, MaxY: $maxY"

        def xOffset = minX
        def xVals = maxX - minX + 1
        def caveGrid = new boolean[xVals][maxY + 1] // values indicate if coordinate is occupied with rock or sand; false is air

        populateCaveWithRocks(paths, caveGrid, xOffset)

        for (y in 0..maxY) {
            StringBuilder sb = new StringBuilder()
            for (x in 0..<caveGrid.length) {
                sb.append(caveGrid[x][y] ? '#' : '.')
            }
            println sb.toString()
        }

        int sandStartX = 500 - xOffset
        int grainsOfSandAtRest = 0

        while (true) {
            def sandAtRest = false
            def x = sandStartX
            for (y in 0..<maxY) {
                if (caveGrid[x][y+1]) { // something immediately below
                    if (x - 1 < 0) { // past edge of grid on left
                        break
                    }

                    if (caveGrid[x - 1][y+1]) { // something down-left
                        if (x + 1 > caveGrid.length) { // past edge of grid on right
                            break
                        }

                        if (caveGrid[x + 1][y+1]) { // something down-right
                            caveGrid[x][y] = true
                            sandAtRest = true
                            grainsOfSandAtRest++
                            break;
                        } else {
                            x++
                        }
                    } else {
                        x--
                    }
                }
            }

            if (!sandAtRest) break;
        }

        return grainsOfSandAtRest
    }

    private void populateCaveWithRocks(ArrayList paths, boolean[][] caveGrid, int xOffset) {
        for (List<XY> path : paths) {
            for (i in 1..<path.size()) {
                XY lineStart = path[i - 1]
                XY lineEnd = path[i]

                if (lineStart.x == lineEnd.x) {
                    // Draw vertically
                    int startY
                    int endY
                    if (lineStart.y < lineEnd.y) {
                        startY = lineStart.y
                        endY = lineEnd.y
                    } else {
                        startY = lineEnd.y
                        endY = lineStart.y
                    }

                    def x = lineStart.x - xOffset
                    for (j in startY..endY) {
                        caveGrid[x][j] = true
                    }
                } else {
                    // Draw horizontally
                    int startX
                    int endX
                    if (lineStart.x < lineEnd.x) {
                        startX = lineStart.x
                        endX = lineEnd.x
                    } else {
                        startX = lineEnd.x
                        endX = lineStart.x
                    }

                    startX -= xOffset
                    endX -= xOffset

                    for (j in startX..endX) {
                        caveGrid[j][lineStart.y] = true
                    }
                }
            }
        }
    }
}
