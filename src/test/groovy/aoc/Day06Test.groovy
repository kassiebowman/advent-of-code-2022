package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit tests for {@link Day06}.
 */
class Day06Test {
    @ParameterizedTest
    @CsvSource([
            "06-control.txt,7,4",
            "06-data.txt,1850,4",
            "06-control.txt,19,14",
            "06-data.txt,2823,14",
    ])
    void findPacketMarker(String fileName, String characters, int markerLength) {
        assertThat(new Day06().findPacketMarker(fileName, markerLength)).isEqualTo(characters)
    }
}