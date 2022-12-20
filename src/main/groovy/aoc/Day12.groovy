package aoc
/**
 * Day 12: Hill Climbing Algorithm
 *
 * @see <a href="https://adventofcode.com/2022/day/12">AOC 2022 Day 12</a>
 * @see <a href="https://stackabuse.com/graphs-in-java-a-star-algorithm/">A* algorithm in Java</a>
 */
class Day12 {
    @SuppressWarnings('GroovyVariableNotAssigned')
    int climbHill(String fileName, boolean part1) {
        def map = getClass().getClassLoader().getResource(fileName).readLines().collect(
                { it.chars.collect(c -> c) })

        Node start
        Node end
        for (row in 0..<map.size()) {
            def column = map[row].findIndexOf { (it == 'S') }
            if (column != -1) {
                start = new Node('a' as char, row, column)
                start.g = 0
                if (end) break
            }

            column = map[row].findIndexOf { (it == 'E') }
            if (column != -1) {
                end = new Node('z' as char, row, column)
                map[row][column] = end.value
                if (start) break
            }
        }

        int minSteps = Integer.MAX_VALUE
        def result
        if (part1) {
            result = findAStarPath(start, end, map)
            minSteps = result.g
        } else {
            map[start.row][start.column] = start.value
            start = end
            start.g = 0

            // Test the other starting positions, routing backwards
            for (row in 0..<map.size()) {
                def column = map[row].findIndexOf { (it == 'a') }
                if (column != -1) {
                    end = new Node('a' as char, row, column)

                    result = findAStarPath(start, end, map, true)
                    if (result.g < minSteps) minSteps = result.g
                }
            }
        }

        return minSteps
    }

    private Node findAStarPath(Node start, Node end, List<List<Character>> map, boolean backwards = false) {
        PriorityQueue<Node> closedNodes = []
        PriorityQueue<Node> openNodes = []

        start.f = start.g + start.getH(end)
        openNodes << start

        while (!openNodes.isEmpty()) {
            def currentNode = openNodes.peek()
//            println("Current node: $currentNode")
            if (currentNode.equals(end)) return currentNode

            def neighbors = currentNode.getNeighbors(map, backwards)
//            println("  Neighbors: $neighbors")
            for (Node neighbor : neighbors) {
                def totalWeight = currentNode.g + 1

                if (!openNodes.contains(neighbor) && !closedNodes.contains(neighbor)) {
                    neighbor.prevNode = currentNode
                    neighbor.g = totalWeight
                    neighbor.f = neighbor.g + neighbor.getH(end)
                    openNodes << neighbor
                } else {
                    if (totalWeight < neighbor.g) {
                        neighbor.prevNode = currentNode
                        neighbor.g = totalWeight
                        neighbor.f = neighbor.g + neighbor.getH(end)

                        if (closedNodes.contains(neighbor)) {
                            closedNodes.remove(neighbor)
                            openNodes << neighbor
                        }
                    }
                }
            }

            openNodes.remove(currentNode)
            closedNodes << currentNode
        }

        return null
    }

    class Node implements Comparable<Node> {
        final char value
        final int row
        final int column
        int f = Integer.MAX_VALUE
        int g = Integer.MAX_VALUE
        private int h = Integer.MAX_VALUE
        private List<Node> neighbors
        Node prevNode

        Node(char value, int row, int column) {
            this.value = value
            this.row = row
            this.column = column
        }

        int getH(Node target) {
            if (h == Integer.MAX_VALUE) {
//                h = Math.max('z' as char - value, Math.abs(row - target.row) + Math.abs(column - target.column))
                h = Math.sqrt((row - target.row) * (row - target.row) + (column - target.column) * (column - target.column))
            }
            return h
        }

        @SuppressWarnings('GroovyVariableNotAssigned')
        List<Node> getNeighbors(List<List<Character>> map, boolean backwards) {
            if (!neighbors) {
                neighbors = []
                if (row > 0) addNeighborIfValid(row - 1, column, map, backwards)
                if (row < map.size() - 1) addNeighborIfValid(row + 1, column, map, backwards)
                if (column > 0) addNeighborIfValid(row, column - 1, map, backwards)
                if (column < map[0].size() - 1) addNeighborIfValid(row, column + 1, map, backwards)
            }

            return neighbors
        }

        @SuppressWarnings('GroovyVariableNotAssigned')
        private void addNeighborIfValid(int row, int column, List<List<Character>> map, boolean backwards) {
            def value = map[row][column]
            def difference = backwards ? this.value - value : value - this.value
            if (value != 'S' && difference <= 1) {
                def neighbor = new Node(value, row, column)
                if (neighbor != prevNode) neighbors << neighbor
            }
        }

        @Override
        int compareTo(Node o) {
            return Integer.compare(f, o.f)
        }

        boolean equals(o) {
            if (this.is(o)) return true
            if (getClass() != o.class) return false

            Node node = (Node) o

            if (column != node.column) return false
            if (row != node.row) return false

            return true
        }

        int hashCode() {
            int result
            result = row
            result = 31 * result + column
            return result
        }

        @Override
        String toString() {
            return "Node{" +
                    "value=" + value +
                    ", row=" + row +
                    ", column=" + column +
                    '}'
        }
    }
}
