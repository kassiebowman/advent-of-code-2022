package aoc
/**
 * Day 2:
 *
 * @see <a href="https://adventofcode.com/2022/day/2"  >  AOC 2022 Day 2</a>
 */
class Day02 {
    def playWithAssignedMoves(String fileName) {
        def score = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine(" ", codes -> {
            def theirMove = HandShape.fromCode(codes[0])
            def myMove = HandShape.fromCode(codes[1])

            def outcome = (myMove == theirMove) ? 3 : myMove.beats(theirMove) ? 6 : 0
            score += (outcome + myMove.points)
        })

        return score
    }

    def playWithAssignedResult(String fileName) {
        def score = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine(" ", codes -> {
            def theirMove = HandShape.fromCode(codes[0])
            def result = Result.fromCode(codes[1])
            def myMove = theirMove.getMyMove(result)

            score += (result.points + myMove.points)
        })

        return score
    }

    private enum HandShape {
        ROCK(1, 2), // 0 beats 2
        PAPER(2, 0), // 1 beats 0
        SCISSORS(3, 1) // 2 beats 1

        static final VALUES = values()
        private final int points
        private final int beatsOrdinal

        HandShape(def points, def beatsOrdinal) {
            this.beatsOrdinal = beatsOrdinal
            this.points = points
        }

        static HandShape fromCode(String code) {
            if (code == "A" || code == "X") return ROCK
            if (code == "B" || code == "Y") return PAPER
            return SCISSORS
        }

        def beats(HandShape otherMove) {
            return otherMove.ordinal() == beatsOrdinal
        }

        def getMyMove(Result result) {
            if (result == Result.DRAW) return this
            if (result == Result.LOSE) return VALUES[(ordinal() + 2) % 3]
            return VALUES[(ordinal() + 1) % 3]
        }
    }

    private enum Result {
        WIN(6),
        DRAW(3),
        LOSE(0)

        private final int points

        Result(def points) {
            this.points = points
        }

        static Result fromCode(String code) {
            if (code == "X") return LOSE
            if (code == "Y") return DRAW
            return WIN
        }
    }
}
