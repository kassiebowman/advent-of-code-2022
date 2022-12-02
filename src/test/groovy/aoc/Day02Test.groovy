package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link Day02}.
 */
class Day02Test
{
    @ParameterizedTest
    @CsvSource([
            "02-control.txt,15",
            "02-data.txt,9651",
    ])
    void testPlayWithAssignedMoves(String fileName, int score) {
        assertThat(new Day02().playWithAssignedMoves(fileName)).isEqualTo(score)
    }

    @ParameterizedTest
    @CsvSource([
            "02-control.txt,12",
            "02-data.txt,10560",
    ])
    void testPlayWithAssignedResult(String fileName, int score) {
        assertThat(new Day02().playWithAssignedResult(fileName)).isEqualTo(score)
    }
}