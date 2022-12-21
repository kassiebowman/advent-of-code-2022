package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day21}.
 */
class Day21Test {
    @ParameterizedTest
    @CsvSource([
            "21-control.txt,152,true",
            "21-data.txt,223971851179174,true",
//            "21-control.txt,301,false",
//            "21-data.txt,671210,false",
    ])
    void doMath(String fileName, long result, boolean part1) {
        assertThat(new Day21().doMath(fileName, part1)).isEqualTo(result)
    }
}