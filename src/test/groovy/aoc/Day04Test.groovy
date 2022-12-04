package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day04}.
 */
class Day04Test {
    @ParameterizedTest
    @CsvSource([
            "04-control.txt,2",
            "04-data.txt,498",
    ])
    void findFullyContainedPairs(String fileName, int containedPairs) {
        assertThat(new Day04().findFullyContainedPairs(fileName)).isEqualTo(containedPairs)
    }

    @ParameterizedTest
    @CsvSource([
            "04-control.txt,4",
            "04-data.txt,859",
    ])
    void findOverlappingPairs(String fileName, int overlappingPairs) {
        assertThat(new Day04().findOverlappingPairs(fileName)).isEqualTo(overlappingPairs)
    }
}