package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day14}.
 */
class Day14Test {
    @ParameterizedTest
    @CsvSource([
            "14-control.txt,24,true",
            "14-data.txt,1133,true",
//            "14-control.txt,8,false",
//            "14-data.txt,671160,false",
    ])
    void fillWithSand(String fileName, int result, boolean part1) {
        assertThat(new Day14().fillWithSand(fileName, part1)).isEqualTo(result)
    }
}