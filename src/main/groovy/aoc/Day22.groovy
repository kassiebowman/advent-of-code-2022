package aoc

import java.util.function.BiFunction
import java.util.function.UnaryOperator

/**
 * Day 22: Monkey Map
 *
 * @see <a href="https://adventofcode.com/2222/day/22">AOC 2222 Day 22</a>
 */
class Day22 {

    public static final String CLOCKWISE_TURN = 'R'
    public static final String COUNTER_CLOCKWISE_TURN = 'L'
    public static final String OPEN_TILE = '.'
    public static final String SOLID_WALL = '#'
    public static final String EMPTY_SPACE = ' '

    int tracePath(String fileName, boolean part1) {
        List<String> board = []
        def lines = getClass().getClassLoader().getResource(fileName).readLines()

        int numRows = lines.size() - 2
        int numCols = 0
        for (i in 0..<numRows) {
            board << lines[i]
            numCols = Math.max(numCols, board.last().size())
        }

        // Pad out board with empty space for rows shorter than the max
        for (i in 0..<numRows) {
            board[i] = board[i].padRight(numCols)
        }

        List<String> actions = getActions(lines.last())

        Facing facing = Facing.RIGHT
        int row = 0
        int col = board[row].findIndexOf {it == OPEN_TILE }

        def newRow
        def newCol
        def nextTile

        for (i in 0..<actions.size()) {
            def action = actions[i]
            if (action.isNumber()) { // move
                for (j in 1..(action as int)) {
                    newRow = row
                    newCol = col
                    do {
                        (newRow,newCol) = facing.moveForwardOneStep(newRow, newCol)
                        if (newRow == -1) newRow += numRows
                        if (newRow == numRows) newRow = 0
                        if (newCol == -1) newCol += numCols
                        if (newCol == numCols) newCol = 0

                        nextTile = board[newRow][newCol]
                    } while (nextTile == EMPTY_SPACE)

                    if (nextTile == OPEN_TILE) {
                        row = newRow
                        col = newCol
                    } else if (nextTile == SOLID_WALL) {
                        break;
                    }
                }
            } else { // turn
                facing = facing.turn(action == CLOCKWISE_TURN)
            }
        }

        return 1000 * (row + 1) + 4 * (col + 1) + facing.getValue()
    }

    List<String> getActions(String line) {
        List<String> actions = []
        int index = 0
        def charCount = line.size()
        while (index < charCount) {
            def turnIndex = line.findIndexOf(index, { it == CLOCKWISE_TURN || it == COUNTER_CLOCKWISE_TURN })
            if (turnIndex != -1) {
                actions << line.substring(index, turnIndex)
                actions << line[turnIndex]
                index = turnIndex + 1
            } else {
                actions << line.substring(index)
                index = charCount
            }
        }

        return actions
    }

    enum Facing {
        RIGHT(0, (r,c) -> new Tuple2(r, c+1)),
        DOWN(1, (r,c) -> new Tuple2(r+1,c)),
        LEFT(2, (r,c) -> new Tuple2(r,c-1)),
        UP(3, (r,c) -> new Tuple2(r-1, c))

        private final int value
        private final BiFunction<Integer, Integer, Tuple2<Integer, Integer>> moveForward

        Facing(int value, BiFunction<Integer, Integer, Tuple2<Integer, Integer>> moveForward) {
            this.moveForward = moveForward
            this.value = value
        }

        int getValue() {
            return value
        }

        Tuple2<Integer, Integer> moveForwardOneStep(int row, int column)
        {
            return moveForward.apply(row, column)
        }

        Facing turn(boolean clockwise)
        {
            switch (this) {
                case RIGHT:
                    return clockwise ? DOWN : UP
                case LEFT:
                    return clockwise ? UP : DOWN
                case UP:
                    return clockwise ? RIGHT : LEFT
                case DOWN:
                    return clockwise ? LEFT : RIGHT
            }
        }
    }
}
