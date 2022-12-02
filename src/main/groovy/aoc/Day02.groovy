package aoc

/**
 * Day 2:
 *
 * @see <ahref="https://adventofcode.com/2022/day/2" > AOC 2022 Day 2</a>
 */
class Day02 {
    def play(String fileName) {
        def score = 0
        getClass().getClassLoader().getResource(fileName).splitEachLine(" ", codes -> {
            def theirMove = HandShape.fromCode(codes[0])
            def myMove = HandShape.fromCode(codes[1])

            def outcome = (myMove == theirMove) ? 3 : myMove.beats(theirMove) ? 6 : 0
            score += (outcome + myMove.points)
        })

        return score
    }

    private enum HandShape {
        ROCK(1, 2),
        PAPER(2, 0),
        SCISSORS(3, 1)

        private final int points
        private final int beatsOrdinal

        HandShape(def points, def beatsOrdinal) {
            this.beatsOrdinal = beatsOrdinal
            this.points = points
        }

        static HandShape fromCode(String code)
        {
            if (code == "A" || code == "X") return ROCK
            if (code == "B" || code == "Y") return PAPER
            return SCISSORS
        }

        def beats(HandShape theirMove)
        {
            return theirMove.ordinal() == beatsOrdinal
        }
    }
}
