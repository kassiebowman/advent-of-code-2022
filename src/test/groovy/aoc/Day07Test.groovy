package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day07}.
 */
class Day07Test {
    @ParameterizedTest
    @CsvSource([
            "07-control.txt,95437,true",
            "07-data.txt,1232307,true",
            "07-control.txt,24933642,false",
            "07-data.txt,7268994,false",
    ])
    void findPacketMarker(String fileName, int result, boolean part1) {
        assertThat(new Day07().findDirectorySize(fileName, part1)).isEqualTo(result)
    }
}