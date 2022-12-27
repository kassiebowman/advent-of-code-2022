package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day25}.
 */
class Day25Test {
    @ParameterizedTest
    @CsvSource([
            "25-control.txt,2=-1=0,true",
            "25-data.txt,2-0-020-1==1021=--01,true",
//            "25-control.txt,8,false",
//            "25-data.txt,671250,false",
    ])
    void heatFuel(String fileName, String result, boolean part1) {
        assertThat(new Day25().heatFuel(fileName, part1)).isEqualTo(result)
    }
}