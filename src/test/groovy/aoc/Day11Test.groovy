package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day11}.
 */
class Day11Test {
    @ParameterizedTest
    @CsvSource([
            "11-control.txt,10605,true",
            "11-data.txt,113232,true",
//            "11-control.txt,8,false",
//            "11-data.txt,671160,false",
    ])
    void playKeepAway(String fileName, int result, boolean part1) {
        assertThat(new Day11().playKeepAway(fileName, part1)).isEqualTo(result)
    }
}