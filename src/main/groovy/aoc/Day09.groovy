package aoc
/**
 * Day 9: Rope Bridge
 *
 * @see <a href="https://adventofcode.com/2022/day/9">AOC 2022 Day 9</a>
 */
class Day09 {
    int countVisitedCoordinates(String fileName, boolean part1) {
        Set<XY> visitedCoords = []

        List<XY> knots = []
        int numKnots = part1 ? 2 : 10
        for (i in 0..<numKnots) {
            knots << new XY(0, 0)
        }

        visitedCoords << new XY(0, 0)

        getClass().getClassLoader().getResource(fileName).splitEachLine(" ", params -> {
            def direction = params[0]
            def numSteps = params[1] as int

            def offsetX = 0
            def offsetY = 0
            if (direction == "R") {
                offsetX = 1
            } else if (direction == 'L') {
                offsetX = -1
            } else if (direction == 'U') {
                offsetY = 1
            } else // D
            {
                offsetY = -1
            }

//            println "== $direction $numSteps =="

            for (j in 1..numSteps) {
                // Move H
                def oldX = knots[0].x
                def oldY = knots[0].y
                knots[0].x += offsetX
                knots[0].y += offsetY

//                println "Moved H from [$oldX, $oldY] to [${knots[0].x}, ${knots[0].y}]"

                // Move adjacent knots
                for (i in 1..<numKnots) {
                    def adjKnot = knots[i - 1]
                    def currKnot = knots[i]

                    oldX = currKnot.x
                    oldY = currKnot.y

                    if (Math.abs(adjKnot.x - currKnot.x) <= 1 && Math.abs(adjKnot.y - currKnot.y) <= 1) {
                        // close enough; don't move
                    } else {
                        def closerX = adjKnot.x > currKnot.x ? 1 : -1
                        def closerY = adjKnot.y > currKnot.y ? 1 : -1

                        if (adjKnot.x == currKnot.x) {
                            currKnot.y += closerY
                        } else if (adjKnot.y == currKnot.y) {
                            currKnot.x += closerX
                        } else { // diagonal
                            currKnot.x += closerX
                            currKnot.y += closerY
                        }
//                        println "  Moved $i from [$oldX, $oldY] to [${currKnot.x}, ${currKnot.y}]"
                    }
                }

                visitedCoords << new XY(knots[numKnots - 1])
            }
        })

        return visitedCoords.size()
    }

    class XY {
        int x
        int y

        XY(int x, int y) {
            this.x = x
            this.y = y
        }

        XY(XY original)
        {
            x = original.x
            y = original.y
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
