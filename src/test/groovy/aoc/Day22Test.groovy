package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day22}.
 */
class Day22Test {
    @ParameterizedTest
    @CsvSource([
            "22-control.txt,6032,true",
            "22-data.txt,106094,true",
//            "22-control.txt,8,false",
//            "22-data.txt,671220,false",
    ])
    void tracePath(String fileName, int result, boolean part1) {
        assertThat(new Day22().tracePath(fileName, part1)).isEqualTo(result)
    }
}