package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for {@link Day02}.
 */
class Day02Test
{
    @ParameterizedTest
    @CsvSource([
            "02-control.txt,15",
            "02-data.txt,9651",
    ])
    void testPlay(String fileName, int score) {
        assertThat(new Day02().play(fileName)).isEqualTo(score)
    }
}