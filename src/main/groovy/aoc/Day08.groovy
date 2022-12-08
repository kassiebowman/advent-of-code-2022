package aoc
/**
 * Day 8: Treetop Tree House
 *
 * @see <ahref="https://adventofcode.com/2022/day/8" > AOC 2022 Day 8</a>
 */
class Day08 {

    int findVisibleTrees(String fileName, boolean part1) {

        def treeRows = getClass().getClassLoader().getResource(fileName).readLines()
        def numColumns = treeRows.size()
        def numRows = treeRows[0].size()

        def visibleInnerTrees = 0
        def maxScenicScore = 0
        for (r in 1..<(numRows - 1)) {
            for (c in 1..<(numColumns - 1)) {
                def indexLeft = getIndexOfFirstTallTreeInRow(r, c, treeRows, true)
                def indexRight = getIndexOfFirstTallTreeInRow(r, c, treeRows, false)
                def indexUp = getIndexOfFirstTallTreeInColumn(r, c, treeRows, true)
                def indexDown = getIndexOfFirstTallTreeInColumn(r, c, treeRows, false)

                if (indexLeft == -1 || indexRight == -1 || indexUp == -1 || indexDown == -1) visibleInnerTrees++
                def scenicScore = (indexUp == -1 ? r : r - indexUp) *
                        (indexLeft == -1 ? c : c - indexLeft) *
                        (indexRight == -1 ? numColumns - 1 - c : indexRight - c) *
                        (indexDown == -1 ? numRows - 1 - r : indexDown - r)
                if (scenicScore > maxScenicScore) maxScenicScore = scenicScore
            }
        }

        return part1 ?
                visibleInnerTrees + 2 * numRows + 2 * (numColumns - 2) :
                maxScenicScore
    }

    boolean isHiddenLeftAndRight(int rowIndex, int columnIndex, List<String> treeRows) {
        def hiddenLeft = getIndexOfFirstTallTreeInRow(rowIndex, columnIndex, treeRows, true) != -1;
        if (!hiddenLeft) return false

        return getIndexOfFirstTallTreeInRow(rowIndex, columnIndex, treeRows, false) != -1;
    }

    int getIndexOfFirstTallTreeInRow(int rowIndex, int columnIndex, List<String> treeRows, boolean left) {
        def treeRow = treeRows[rowIndex]
        def tree = treeRow[columnIndex] as int

        def columnIndices = left ? (0..<columnIndex).reverse() : ((columnIndex + 1)..<treeRow.size())
        for (i in columnIndices) {
            if (treeRow[i] as int >= tree) {
                return i
            }
        }

        return -1
    }

    int getIndexOfFirstTallTreeInColumn(int rowIndex, int columnIndex, List<String> treeRows, boolean up) {
        def tree = treeRows[rowIndex][columnIndex] as int

        def rowIndices = up ? (0..<rowIndex).reverse() : (rowIndex + 1)..<treeRows.size()
        for (i in rowIndices) {
            if (treeRows[i][columnIndex] as int >= tree) {
                return i
            }
        }

        return -1
    }
}
