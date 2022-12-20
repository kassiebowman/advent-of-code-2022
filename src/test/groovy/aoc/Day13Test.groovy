package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day13}.
 */
class Day13Test {
    @ParameterizedTest
    @CsvSource([
            "13-control.txt,13,true",
            "13-data.txt,6272,true",
//            "13-control.txt,8,false",
//            "13-data.txt,671160,false",
    ])
    void orderPackets(String fileName, int result, boolean part1) {
        assertThat(new Day13().orderPackets(fileName, part1)).isEqualTo(result)
    }
}