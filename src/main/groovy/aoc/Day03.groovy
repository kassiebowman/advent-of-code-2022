package aoc

/**
 * Day 3: Rucksack Reorganization
 *
 * @see <ahref="https://adventofcode.com/2022/day/3"  >  AOC 2022 Day 3</a>
 */
class Day03 {
    int UPPERCASE_OFFSET = 27 - (char) 'A'
    int LOWERCASE_OFFSET = 1 - (char) 'a'

    /**
     * Determines the items that are contained in both compartment for each rucksack.
     *
     * @param fileName The name of the file containing the list of contents for each rucksack as a list of characters
     * @return Sum of priorities for items that are present in both rucksack compartments.
     */
    int reorganizeRucksacks(String fileName) {
        int sum = 0
        getClass().getClassLoader().getResource(fileName).eachLine { line ->
            {
                int itemsPerCompartment = line.size() / 2
                def compartment1 = line.substring(0, itemsPerCompartment)
                def compartment2 = line.substring(itemsPerCompartment)

                def commonChars = compartment1.chars as List
                commonChars.retainAll(compartment2.chars)
                char itemInBoth = commonChars[0]

                sum += itemInBoth + (itemInBoth.isUpperCase() ? UPPERCASE_OFFSET : LOWERCASE_OFFSET)
            }
        }

        return sum
    }

    /**
     * Determine the items used as a badge for each group of three elves.
     *
     * @param fileName The name of the file containing the list of contents for each rucksack as a list of characters
     * @return Sum of priorities for items used as badges for the elf groups.
     */
    int findBadges(String fileName) {
        int sum = 0
        int lineCount = 0
        def commonChars
        getClass().getClassLoader().getResource(fileName).eachLine { line ->
            {
                lineCount++
                if (lineCount == 1) {
                    commonChars = line.chars as List
                } else if (lineCount == 2) {
                    commonChars.retainAll(line.chars)
                } else {
                    commonChars.retainAll(line.chars)
                    char itemInBoth = commonChars[0]

                    sum += itemInBoth + (itemInBoth.isUpperCase() ? UPPERCASE_OFFSET : LOWERCASE_OFFSET)

                    lineCount = 0
                }
            }
        }

        return sum
    }
}
