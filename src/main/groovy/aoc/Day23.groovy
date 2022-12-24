package aoc

import aoc.utils.XY

/**
 * Day 23: Unstable Diffusion
 *
 * @see <a href="https://adventofcode.com/2323/day/23">AOC 2323 Day 23</a>
 */
class Day23 {
    int plantTrees(String fileName, boolean part1) {
        List<Elf> elves = []
        def startY = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine("") {
            for (i in 0..<it.size()) {
                if (it[i] == '#') elves << new Elf(new XY(i,startY))
            }

            startY--
        }

        List<Direction> moveOrder = Direction.values()

        for (i in 0..<10) {
            planMovement(elves, moveOrder)
            move(elves)
            moveOrder.add(moveOrder.remove(0))
        }

        // Count empty spaces in bounding rectangle
        int minX = Integer.MAX_VALUE
        int maxX = Integer.MIN_VALUE
        int minY = Integer.MAX_VALUE
        int maxY = Integer.MIN_VALUE
        for (i in 0..<elves.size()) {
            XY xy = elves[i].currentLoc
            minX = Math.min(minX, xy.x)
            maxX = Math.max(maxX, xy.x)
            minY = Math.min(minY, xy.y)
            maxY = Math.max(maxY, xy.y)
        }

        return (maxX - minX + 1) * (maxY - minY + 1) - elves.size()
    }

    void planMovement(List<Elf> elves, List<Direction> moveOrder) {
        for (i in 0..<elves.size()) {
            def elf = elves[i]
            def x = elf.currentLoc.x
            def y = elf.currentLoc.y
            elf.plannedLoc = null

            def N = isOccupied(x, y+1, elves)
            def S = isOccupied(x, y-1, elves)
            def E = isOccupied(x+1, y, elves)
            def W = isOccupied(x-1, y, elves)
            def NE = isOccupied(x+1, y+1, elves)
            def NW = isOccupied(x-1, y+1, elves)
            def SE = isOccupied(x+1, y-1, elves)
            def SW = isOccupied(x-1, y-1, elves)

            // No adjacent elves; don't need to move
            if (!(N || S || E || W || NE || NW || SE || SW)) {
                continue
            }

            for (j in 0..<moveOrder.size()) {
                def direction = moveOrder[j]
                if (direction == Direction.N) {
                    if (!N && !NE && !NW) {
                        elf.plannedLoc = new XY(x, y + 1)
                        break
                    }
                } else if (direction == Direction.S) {
                    if (!S && !SE && !SW) {
                        elf.plannedLoc = new XY(x, y-1)
                        break
                    }
                } else if (direction == Direction.W) {
                    if (!W && !NW && !SW) {
                        elf.plannedLoc = new XY(x-1, y)
                        break
                    }
                } else { // E
                    if (!E && !NE && !SE) {
                        elf.plannedLoc = new XY(x+1, y)
                        break
                    }
                }
            }
        }
    }

    boolean isOccupied(int x, int y, List<Elf> elves) {
        XY loc = new XY(x,y)
        return elves.stream().anyMatch(elf -> elf.currentLoc == loc)
    }

    void move(List<Elf> elves) {
        for (i in 0..<elves.size()) {
            def elf = elves[i]
            def plannedLoc = elf.plannedLoc
            if (!plannedLoc) continue
            if (!isPlannedByOther(plannedLoc, elf, elves)) elf.currentLoc = plannedLoc
        }
    }

    boolean isPlannedByOther(XY loc, Elf thisElf, List<Elf> elves) {
        return elves.stream().filter(elf -> elf != thisElf).anyMatch(elf -> elf.plannedLoc == loc)
    }

    class Elf {
        XY currentLoc
        XY plannedLoc

        Elf(XY currentLoc) {
            this.currentLoc = currentLoc
        }
    }

    enum Direction {
        N,
        S,
        W,
        E
    }
}
