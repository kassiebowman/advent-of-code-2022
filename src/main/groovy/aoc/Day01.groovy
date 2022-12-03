package aoc

/**
 * Day 1: Calorie Counting
 *
 * @see <a href="https://adventofcode.com/2022/day/1">AOC 2022 Day 1</a>
 */
class Day01 {
    /**
     * Determines how many calories each elf is carrying and returns a sorted set of calories/elf with the elf
     * carrying the most calories listed first.
     *
     * @param fileName The name of the file containing the calorie counts, grouped by elf with blank lines in between
     * @return A sorted set of calories/elf, with the largest number of calories listed first
     */
    def countCalories(String fileName)
    {
        def caloriesPerElf = new TreeSet<>(Comparator.reverseOrder())
        def caloriesForCurrentElf = 0
        getClass().getClassLoader().getResource(fileName).eachLine {line ->
            if (!line) {
                caloriesPerElf << caloriesForCurrentElf
                caloriesForCurrentElf = 0
            } else {
                caloriesForCurrentElf += line as int
            }
        }

        // Add the value for the last elf
        caloriesPerElf << caloriesForCurrentElf

        return caloriesPerElf
    }

    /**
     * Determines the total number of calories carried by {@code numberOfElves} who have the highest number of calories.
     *
     * @param fileName The name of the file containing the calorie counts, grouped by elf with blank lines in between
     * @param numberOfElves The number of elves to include in the max calories count
     * @return Total calories carried by the top elves.
     */
    int getCaloriesForTopElves(String fileName, int numberOfElves)
    {
        return countCalories(fileName).take(numberOfElves).sum()
    }
}
