package aoc
/**
 * Day 8: Treetop Tree House
 *
 * @see <a href="https://adventofcode.com/2022/day/8"  >  AOC 2022 Day 8</a>
 */
class Day08 {
    /**
     * Checks the visibility of trees to determine which trees are visible outside the forest or which have the farthest
     * sight lines within the forest.
     *
     * @param fileName The name of the file containing the tree heights, as a list of strings
     * @param part1 Whether to return the number of visible trees of the highest scenic score
     * @return If part1 is {@code true}, returns the number of visible trees. If part1 is {@code false}, returns the
     * highest scenic score of any tree.
     */
    int checkTreeVisibility(String fileName, boolean part1) {
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

    /**
     * Gets the column index of the first tree in the row with a height greater than or equal to the reference tree at
     * the specified row and column indices.
     *
     * @param rowIndex The row index of the reference tree
     * @param columnIndex The column index of the reference tree
     * @param treeRows List of tree rows
     * @param left {@code true} if looking for a tall tree to the left; {@code false} to look for a tall tree to the right
     * @return The column index of the first tree with height greater than or equal to the reference tree in the
     * specified direction (left or right), or -1 if no trees are of greater or equal height
     */
    int getIndexOfFirstTallTreeInRow(int rowIndex, int columnIndex, List<String> treeRows, boolean left) {
        def treeRow = treeRows[rowIndex]
        def tree = treeRow[columnIndex] as int

        def columnIndices = left ? (columnIndex - 1)..0 : ((columnIndex + 1)..<treeRow.size())
        for (i in columnIndices) {
            if (treeRow[i] as int >= tree) {
                return i
            }
        }

        return -1
    }

    /**
     * Gets the row index of the first tree in the column with a height greater than or equal to the reference tree at
     * the specified row and column indices.
     *
     * @param rowIndex The row index of the reference tree
     * @param columnIndex The column index of the reference tree
     * @param treeRows List of tree rows
     * @param up {@code true} if looking for a tall tree above the reference tree; {@code false} to look for a tall tree below
     * @return The row index of the first tree with height greater than or equal to the reference tree in the
     * specified direction (up or down), or -1 if no trees are of greater or equal height
     */
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
