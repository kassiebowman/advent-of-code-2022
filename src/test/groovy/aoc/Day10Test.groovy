package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day10}.
 */
class Day10Test {
    @ParameterizedTest
    @CsvSource([
            "10-control.txt,13140,true",
            "10-data.txt,13920,true",
//            "10-control.txt,8,false",
//            "10-data.txt,671160,false",
    ])
    void findSignalStrength(String fileName, int result, boolean part1) {
        assertThat(new Day10().findSignalStrength(fileName, part1)).isEqualTo(result)
    }
}