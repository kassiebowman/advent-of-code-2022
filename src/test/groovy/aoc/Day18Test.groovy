package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day18}.
 */
class Day18Test {
    @ParameterizedTest
    @CsvSource([
            "18-control.txt,64,true",
            "18-data.txt,4444,true",
//            "18-control.txt,58,false",
//            "18-data.txt,0,false",
    ])
    void calculateSurfaceArea(String fileName, int result, boolean part1) {
        assertThat(new Day18().calculateSurfaceArea(fileName, part1)).isEqualTo(result)
    }
}