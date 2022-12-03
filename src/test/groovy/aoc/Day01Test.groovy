package aoc

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import static org.assertj.core.api.Assertions.assertThat

/**
 * Unit test for {@link Day01}.
 */
class Day01Test {
    @ParameterizedTest
    @CsvSource([
            "01-control.txt,24000,1",
            "01-data.txt,74394,1",
            "01-control.txt,45000,3",
            "01-data.txt,212836,3",
    ])
    void countCalories(String fileName, int maxCalories, int elves) {
        assertThat(new Day01().getCaloriesForTopElves(fileName, elves)).isEqualTo(maxCalories)
    }
}