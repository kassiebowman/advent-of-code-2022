package aoc
/**
 * Day 18: Boiling Boulders
 *
 * @see <ahref="https://adventofcode.com/2022/day/18"    >    AOC 2022 Day 18</a>
 */
class Day18 {
    int calculateSurfaceArea(String fileName, boolean part1) {
        List<Cube> cubes = []
        Cube currentCube = null
        getClass().getClassLoader().getResource(fileName).splitEachLine(",", coords -> {
            def cube = new Cube(coords[0] as int, coords[1] as int, coords[2] as int)
            cubes << cube

            if (!currentCube) {
                currentCube = cube
            } else if (currentCube.origin.x > cube.origin.x) {
                currentCube = cube
            } else if (currentCube.origin.x == cube.origin.x && currentCube.origin.y > cube.origin.y) {
                currentCube = cube
            } else if (currentCube.origin.x == cube.origin.x && currentCube.origin.y == cube.origin.y && currentCube.origin.z > cube.origin.z) {
                currentCube = cube
            }
        })

        cubes.remove(currentCube)
        List<Cube> cubesToAdd = [currentCube]
        List<Side> polygonSides = []

        while (!cubes.isEmpty()) {
            currentCube = !cubesToAdd.isEmpty() ? cubesToAdd.remove(0) : cubes.remove(0)

            // Remove any sides in the polygon that are adjacent to this cube and add any new sides of this cube
            def adjacentSides = currentCube.sides.stream().filter(side -> polygonSides.contains(side)).toList()
            def newSides = []
            newSides.addAll(currentCube.sides)
            newSides.removeAll(adjacentSides)
            polygonSides.removeAll(adjacentSides)
            polygonSides.addAll(newSides)

            for (Side side : newSides) {
                def cubesWithSide = cubes.stream().filter(cube -> cube.hasSide(side)).toList()
                cubes.removeAll(cubesWithSide)
                cubesToAdd.addAll(cubesWithSide)
            }
        }

        return polygonSides.size()


//        def exposedSides = 0
//        cubes.forEach(cube -> {
//            // Check each side
//            if (isSideExposed(cube, [1, 0, 0], cubes)) exposedSides++
//            if (isSideExposed(cube, [-1, 0, 0], cubes)) exposedSides++
//            if (isSideExposed(cube, [0, 1, 0], cubes)) exposedSides++
//            if (isSideExposed(cube, [0, -1, 0], cubes)) exposedSides++
//            if (isSideExposed(cube, [0, 0, 1], cubes)) exposedSides++
//            if (isSideExposed(cube, [0, 0, -1], cubes)) exposedSides++
//        })
//
//        return exposedSides
    }

//    boolean isSideExposed(Cube cube, List<Integer> sideOffsets, List<Cube> cubes) {
//        def adjCube = new Cube(cube, sideOffsets)
//        return cubes.stream().noneMatch(it -> it == adjCube)
//    }

    class XYZ {
        final int x
        final int y
        final int z

        XYZ(int x, int y, int z) {
            this.x = x
            this.y = y
            this.z = z
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            XYZ xyz = (XYZ) o

            if (x != xyz.x) return false
            if (y != xyz.y) return false
            if (z != xyz.z) return false

            return true
        }

        int hashCode() {
            int result
            result = x
            result = 31 * result + y
            result = 31 * result + z
            return result
        }

        @Override
        String toString() {
            return "[" + x +
                    "," + y +
                    "," + z +
                    '}]';
        }
    }

    class Side {
        final List<XYZ> vertices
        final Plane plane

        Side(List<XYZ> vertices) {
            this.vertices = vertices
            plane = getPlane()
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            Side side = (Side) o

            if (vertices.size() != side.vertices.size()) return false
            return vertices.containsAll(side.vertices)
        }

        int hashCode() {
            return vertices.hashCode()
        }

        @Override
        String toString() {
            return "Side{" +
                    "plane=" + plane +
                    ", vertices=" + vertices +
                    '}';
        }

        Plane getPlane() {
            if (isXYPlane()) return Plane.XY
            if (isYZPlane()) return Plane.YZ
            return Plane.XZ
        }

        boolean isXYPlane() {
            return vertices.stream().allMatch(vertex -> vertex.z == vertices[0].z)
        }

        boolean isYZPlane() {
            return vertices.stream().allMatch(vertex -> vertex.x == vertices[0].x)
        }
    }

    enum Plane
    {
        XY,
        YZ,
        XZ
    }

    class Cube {
        final XYZ origin
        final List<Side> sides = []
        final List<XYZ> vertices = []

        Cube(int x, int y, int z) {
            origin = new XYZ(x, y, z)
            vertices << origin
            vertices << offset(origin, 1, 0, 0)
            vertices << offset(origin, 1, 1, 0)
            vertices << offset(origin, 0, 1, 0)
            vertices << offset(origin, 0, 0, 1)
            vertices << offset(origin, 1, 0, 1)
            vertices << offset(origin, 1, 1, 1)
            vertices << offset(origin, 0, 1, 1)

            sides << new Side([vertices[0], vertices[1], vertices[2], vertices[3]])
            sides << new Side([vertices[1], vertices[5], vertices[6], vertices[2]])
            sides << new Side([vertices[5], vertices[4], vertices[7], vertices[6]])
            sides << new Side([vertices[4], vertices[0], vertices[3], vertices[7]])
            sides << new Side([vertices[0], vertices[1], vertices[5], vertices[4]])
            sides << new Side([vertices[3], vertices[2], vertices[6], vertices[7]])
        }

        XYZ offset(XYZ base, int xOffset, int yOffset, int zOffset) {
            return new XYZ(base.x + xOffset, base.y + yOffset, base.z + zOffset)
        }

        boolean hasSide(Side side) {
            return sides.contains(side)
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            Cube cube = (Cube) o

            if (origin != cube.origin) return false

            return true
        }

        int hashCode() {
            return origin.hashCode()
        }

        @Override
        String toString() {
            return "Cube{" +
                    "origin=" + origin +
                    '}';
        }
    }
}
