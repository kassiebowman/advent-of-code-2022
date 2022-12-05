package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day05}.
 */
class Day05Test {
    @ParameterizedTest
    @CsvSource([
            "05-control.txt,CMZ, false",
            "05-data.txt,ZWHVFWQWW, false",
            "05-control.txt,MCD, true",
            "05-data.txt,HZFZCCWWV, true",
    ])
    void findTopCrates(String fileName, String topCrates, boolean moveTogether) {
        assertThat(new Day05().findTopCrates(fileName, moveTogether)).isEqualTo(topCrates)
    }
}