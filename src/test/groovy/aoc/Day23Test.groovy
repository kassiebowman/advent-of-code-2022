package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day23}.
 */
class Day23Test {
    @ParameterizedTest
    @CsvSource([
            "23-control.txt,110,true",
            "23-data.txt,3689,true",
//            "23-control.txt,8,false",
//            "23-data.txt,671230,false",
    ])
    void plantTrees(String fileName, int result, boolean part1) {
        assertThat(new Day23().plantTrees(fileName, part1)).isEqualTo(result)
    }
}