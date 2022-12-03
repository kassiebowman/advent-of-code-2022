package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day03}.
 */
class Day03Test {
    @ParameterizedTest
    @CsvSource([
            "03-control.txt,157",
            "03-data.txt,7691",
    ])
    void reorganizeRucksacks(String fileName, int prioritySum) {
        assertThat(new Day03().reorganizeRucksacks(fileName)).isEqualTo(prioritySum)
    }
    @ParameterizedTest
    @CsvSource([
            "03-control.txt,70",
            "03-data.txt,2508",
    ])
    void findBadges(String fileName, int prioritySum) {
        assertThat(new Day03().findBadges(fileName)).isEqualTo(prioritySum)
    }
}