package aoc

/**
 * Day 2: Rock Paper Scissors
 *
 * @see <a href="https://adventofcode.com/2022/day/2">AOC 2022 Day 2</a>
 */
class Day02 {

    def codeToHandShape = ["A": HandShape.ROCK, "B": HandShape.PAPER, "C": HandShape.SCISSORS,
                           "X": HandShape.ROCK, "Y": HandShape.PAPER, "Z": HandShape.SCISSORS]

    def codeToResult = ["X": RoundResult.LOSE, "Y": RoundResult.DRAW, "Z": RoundResult.WIN]

    /**
     * Play rock, paper, scissors based on the strategy in the provided file, where the strategy provides the moves for
     * both sides.
     *
     * @param fileName The name of the file containing the game strategy
     * @return The total score achieved by playing according to the guide.
     */
    int playWithAssignedMoves(String fileName) {
        def score = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine(" ", codes -> {
            def theirMove = codeToHandShape[codes[0]]
            def myMove = codeToHandShape[codes[1]]
            def result = RoundResult.fromMoves(myMove, theirMove)

            score += result.points + myMove.points
        })

        return score
    }

    /**
     * Play rock, paper, scissors based on the strategy in the provided file, where the strategy provides the move for
     * the opponent and the desired result.
     *
     * @param fileName The name of the file containing the game strategy
     * @return The total score achieved by playing according to the guide.
     */
    int playWithAssignedResult(String fileName) {
        def score = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine(" ", codes -> {
            def theirMove = codeToHandShape[codes[0]]
            def result = codeToResult[codes[1]]
            def myMove = theirMove.getMyMove(result)

            score += result.points + myMove.points
        })

        return score
    }

    /**
     * Enum representing the hand shapes in the game.
     */
    private enum HandShape {
        ROCK(1, 2),
        PAPER(2, 0),
        SCISSORS(3, 1)

        static final VALUES = values()
        private final int points
        private final int beatsOrdinal

        HandShape(def points, def beatsOrdinal) {
            this.beatsOrdinal = beatsOrdinal
            this.points = points
        }

        /**
         * @param result The desired result
         * @return The move needed to achieve the desired result against this hand shape.
         */
        def getMyMove(RoundResult result) {
            if (result == RoundResult.DRAW) return this
            if (result == RoundResult.LOSE) return VALUES[(ordinal() + 2) % 3]
            return VALUES[(ordinal() + 1) % 3]
        }
    }

    /**
     * Enum representing the result of the round.
     */
    private enum RoundResult {
        WIN(6),
        DRAW(3),
        LOSE(0)

        private final int points

        RoundResult(def points) {
            this.points = points
        }

        /**
         * @return The result of the round with the given moves by each player.
         */
        static def fromMoves(HandShape myMove, HandShape theirMove) {
            if (myMove == theirMove) return DRAW
            if (myMove.beatsOrdinal == theirMove.ordinal()) return WIN
            return LOSE
        }
    }
}
