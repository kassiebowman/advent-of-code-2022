package aoc
/**
 * Day 18: Boiling Boulders
 *
 * @see <a href="https://adventofcode.com/2022/day/18">AOC 2022 Day 18</a>
 */
class Day18 {
    int calculateSurfaceArea(String fileName, boolean part1) {
        def cubes = []
        getClass().getClassLoader().getResource(fileName).splitEachLine(",", coords -> {
            cubes << new Cube(coords)
        })

        def exposedSides = 0
        cubes.forEach(cube -> {
            // Check each side
            if (isSideExposed(cube, [1, 0, 0], cubes)) exposedSides++
            if (isSideExposed(cube, [-1, 0, 0], cubes)) exposedSides++
            if (isSideExposed(cube, [0, 1, 0], cubes)) exposedSides++
            if (isSideExposed(cube, [0, -1, 0], cubes)) exposedSides++
            if (isSideExposed(cube, [0, 0, 1], cubes)) exposedSides++
            if (isSideExposed(cube, [0, 0, -1], cubes)) exposedSides++
        })

        return exposedSides
    }

    boolean isSideExposed(Cube cube, List<Integer> sideOffsets, List<Cube> cubes)
    {
        def adjCube = new Cube(cube, sideOffsets)
        return cubes.stream().noneMatch(it -> it == adjCube)
    }

    class Cube
    {
        private final int x
        private final int y
        private final int z

        Cube(List<Integer> originCoordinates)
        {
            this.x = originCoordinates[0] as int
            this.y = originCoordinates[1] as int
            this.z = originCoordinates[2] as int
        }

        Cube(Cube original, List<Integer> offsetCoordinates)
        {
            this.x = original.x + offsetCoordinates[0]
            this.y = original.y + offsetCoordinates[1]
            this.z = original.z + offsetCoordinates[2]
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            Cube cube = (Cube) o

            if (x != cube.x) return false
            if (y != cube.y) return false
            if (z != cube.z) return false

            return true
        }

        int hashCode() {
            int result
            result = x
            result = 31 * result + y
            result = 31 * result + z
            return result
        }
    }
}
