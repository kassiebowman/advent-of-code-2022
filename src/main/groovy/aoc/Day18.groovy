package aoc

import java.util.stream.Collectors

/**
 * Day 18: Boiling Boulders
 *
 * @see <ahref="https://adventofcode.com/2022/day/18" > AOC 2022 Day 18</a>
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
        List<Cube> cubesToAdd = [currentCube] // Cubes to add to the current surface
        Surface surface = new Surface()
        List<Edge> sharedEdges = []

        while (!cubes.isEmpty()) {
            if (!cubesToAdd.isEmpty()) {
                currentCube = cubesToAdd.remove(0)
            } else {
                currentCube = cubes.remove(0)
                sharedEdges << currentCube.edges.stream().filter(e -> surface.edges.contains(e)).collect(Collectors.toSet())
            }

            // Remove any faces in the surface that are adjacent to this cube and add any new faces of this cube
            def adjacentFaces = currentCube.faces.stream().filter(face -> surface.faces.contains(face)).toList()
            def newFaces = []
            newFaces.addAll(currentCube.faces)
            newFaces.removeAll(adjacentFaces)
            surface.removeAll(adjacentFaces)
            surface.addAll(newFaces)

            for (Face face : newFaces) {
                def cubesWithFace = cubes.stream().filter(cube -> cube.hasFace(face)).toList()
                cubes.removeAll(cubesWithFace)
                cubesToAdd.addAll(cubesWithFace)
            }
        }

        return surface.faces.size()
    }

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
                    '}]'
        }
    }

    class Edge {
        final List<XYZ> vertices

        Edge(List<XYZ> vertices) {
            this.vertices = vertices
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            Edge edge = (Edge) o

            if (vertices.size() != edge.vertices.size()) return false
            return vertices.containsAll(edge.vertices)
        }

        int hashCode() {
            return vertices.hashCode()
        }

    }

    class Face {
        final List<Edge> edges = []
        final List<XYZ> vertices = []
        final Plane plane

        Face(List<XYZ> vertices) {
            this.vertices.addAll(vertices)

            if (isXYPlane()) {
                plane = Plane.XY
            } else if (isYZPlane()) {
                plane = Plane.YZ
            } else {
                plane = Plane.XZ
            }

            for (i in 1..<vertices.size()) {
                edges << new Edge([vertices[i - 1], vertices[i]])
            }

            edges << new Edge([vertices.last(), vertices.first()])
        }

        private boolean isXYPlane() {
            return vertices.stream().allMatch(vertex -> vertex.z == vertices[0].z)
        }

        private boolean isYZPlane() {
            return vertices.stream().allMatch(vertex -> vertex.x == vertices[0].x)
        }

        boolean hasEdge(Edge edge) {
            return edges.contains(edge)
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            Face face = (Face) o

            if (vertices.size() != face.vertices.size()) return false
            return vertices.containsAll(face.vertices)
        }

        int hashCode() {
            return vertices.hashCode()
        }

        @Override
        String toString() {
            return "Face{" +
                    "plane=" + plane +
                    ", vertices=" + vertices +
                    '}'
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
        final Set<Edge> edges
        final List<Face> faces = []
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

            faces << new Face([vertices[0], vertices[1], vertices[2], vertices[3]])
            faces << new Face([vertices[1], vertices[5], vertices[6], vertices[2]])
            faces << new Face([vertices[5], vertices[4], vertices[7], vertices[6]])
            faces << new Face([vertices[4], vertices[0], vertices[3], vertices[7]])
            faces << new Face([vertices[0], vertices[1], vertices[5], vertices[4]])
            faces << new Face([vertices[3], vertices[2], vertices[6], vertices[7]])

            edges = faces.stream().map(f -> f.edges).collect(Collectors.toSet())
        }

        XYZ offset(XYZ base, int xOffset, int yOffset, int zOffset) {
            return new XYZ(base.x + xOffset, base.y + yOffset, base.z + zOffset)
        }

        boolean hasEdge(Edge edge) {
            return edges.contains(edge)
        }

        boolean hasFace(Face face) {
            return faces.contains(face)
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
                    '}'
        }
    }

    class Surface {
        Set<Edge> edges = []
        List<Face> faces = []

        void addAll(List<Face> faces) {
            this.faces.addAll(faces)
            edges.addAll(faces.stream().map(f -> f.edges).collect(Collectors.toSet()))
        }

        void removeAll(List<Face> faces) {
            this.faces.removeAll(faces)
        }

        boolean hasEdge(Edge edge) {
            return edges.contains(edge)
        }
    }
}
