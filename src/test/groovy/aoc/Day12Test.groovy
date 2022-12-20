package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day12}.
 */
class Day12Test {
    @ParameterizedTest
    @CsvSource([
            "12-control.txt,31,true",
            "12-data.txt,447,true",
            "12-control.txt,29,false",
            "12-data.txt,446,false",
    ])
    void climbHill(String fileName, int result, boolean part1) {
        assertThat(new Day12().climbHill(fileName, part1)).isEqualTo(result)
    }
}