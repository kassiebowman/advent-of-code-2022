package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day08}.
 */
class Day08Test {
    @ParameterizedTest
    @CsvSource([
            "08-control.txt,21,true",
            "08-data.txt,1763,true",
            "08-control.txt,8,false",
            "08-data.txt,671160,false",
    ])
    void checkTreeVisibility(String fileName, int result, boolean part1) {
        assertThat(new Day08().checkTreeVisibility(fileName, part1)).isEqualTo(result)
    }
}