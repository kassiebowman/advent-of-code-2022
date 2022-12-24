package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day15}.
 */
class Day15Test {
    @ParameterizedTest
    @CsvSource([
            "15-control.txt,10,26,true",
            "15-data.txt,2000000,4725496,true",
//            "15-control.txt,8,false",
//            "15-data.txt,671160,false",
    ])
    void excludeBeacons(String fileName, long row, int result, boolean part1) {
        assertThat(new Day15().excludeBeacons(fileName, row, part1)).isEqualTo(result)
    }
}