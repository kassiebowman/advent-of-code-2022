package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day09}.
 */
class Day09Test {
    @ParameterizedTest
    @CsvSource([
            "09-control.txt,13,true",
            "09-data.txt,6044,true",
            "09-control.txt,1,false",
            "09-control2.txt,36,false",
            "09-data.txt,2384,false",
    ])
    void countVisitedCoordinates(String fileName, int result, boolean part1) {
        assertThat(new Day09().countVisitedCoordinates(fileName, part1)).isEqualTo(result)
    }
}