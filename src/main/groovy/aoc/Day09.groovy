package aoc
/**
 * Day 9: Rope Bridge
 *
 * @see <ahref="https://adventofcode.com/2022/day/9">AOC 2022 Day 9</a>
 */
class Day09 {
    int countVisitedCoordinates(String fileName, boolean part1) {
        Set<XY> visitedCoords = []
        visitedCoords << new XY(0,0)

        def headX = 0
        def headY = 0
        def tailX = 0
        def tailY = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine(" ", params -> {
            def direction = params[0]
            def numSteps = params[1] as int

            def offsetX = 0
            def offsetY = 0
            if (direction == "R")
            {
                offsetX = 1
            } else if (direction == 'L')
            {
                offsetX = -1
            } else if (direction == 'U')
            {
                offsetY = 1
            } else // D
            {
                offsetY = -1
            }

            for (i in 1..numSteps) {
                // Move H
                headX += offsetX
                headY += offsetY

                // Move tail
                if (Math.abs(headX - tailX) <= 1 && Math.abs(headY - tailY) <= 1) {
                    // close enough; don't move T
                } else {
                    if (headX == tailX) {
                        tailY += offsetY
                    } else if (headY == tailY) {
                        tailX += offsetX
                    } else { // diagonal
                        tailX += headX > tailX ? 1 : -1
                        tailY += headY > tailY ? 1 : -1
                    }

                    visitedCoords << new XY(tailX, tailY)
                }
            }
        })

        return visitedCoords.size()
    }

    class XY {
        final int x
        final int y

        XY(int x, int y) {
            this.x = x
            this.y = y
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            XY xy = (XY) o

            if (x != xy.x) return false
            if (y != xy.y) return false

            return true
        }

        int hashCode() {
            int result
            result = x
            result = 31 * result + y
            return result
        }
    }
}
