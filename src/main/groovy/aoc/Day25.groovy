package aoc
/**
 * Day 25: Full of Hot Air
 *
 * @see <a href="https://adventofcode.com/2525/day/25">AOC 2525 Day 25</a>
 */
class Day25 {
    long sum = 0

    String heatFuel(String fileName, boolean part1) {
        getClass().getClassLoader().getResource(fileName).splitEachLine("") {
            def numDigits = it.size()
            long placeMultiplier = 1
            long decodedValue = 0
            for (i in (numDigits -1)..0) {
                def value = Digit.fromNumeral(it[i]).value
                decodedValue += value * placeMultiplier
                placeMultiplier *= 5
            }

            def snafu = convertToSnafu(decodedValue)
            assert it.join() == snafu
//            println("${it.join()}:\t$decodedValue")
            sum += decodedValue
        }

        println("Base 10 sum: $sum")

        return convertToSnafu(sum)
    }

    private String convertToSnafu(long currentValue) {
        // Build up place list until the highest digit is found
        List<Place> places = []
        int powerOf5 = 0
        def maxOfPreviousPlaces = 0
        places << new Place(powerOf5++, maxOfPreviousPlaces)
        maxOfPreviousPlaces += places[0].valueOf2
        while (true) {
            places.add(0, new Place(powerOf5++, maxOfPreviousPlaces))
            maxOfPreviousPlaces += places[0].valueOf2
            if (getDistance(currentValue, places[0].valueOf1) > getDistance(currentValue, places[1].valueOf1)) break
        }
        StringBuffer sb = new StringBuffer()
        for (i in 0..<places.size()) {
            if (currentValue == 0) {
                sb.append(0)
                continue
            }

            Place currentPlace = places[i]
            def valueOf1 = currentPlace.valueOf1
            def valueOf2 = currentPlace.valueOf2
            def maxOfPrevious = currentPlace.maxOfPreviousPlaces

            def positive = currentValue > 0

            if (Math.abs(currentValue) == valueOf1) {
                sb.append(positive ? 1 : "-")
                currentValue -= positive ? valueOf1 : -valueOf1
            } else if (Math.abs(currentValue) == valueOf2) {
                sb.append(positive ? 2 : "=")
                currentValue -= positive ? valueOf2 : -valueOf2
            } else {
                def distance1 = getDistance(currentValue, valueOf1)
                def distance2 = getDistance(currentValue, valueOf2)

                if (distance2 < distance1) {
                    sb.append(positive ? 2 : "=")
                    currentValue -= positive ? valueOf2 : -valueOf2
                } else {
                    def nextPlaceValueOf2 = places[i + 1].valueOf2
                    def distanceNextDigit = getDistance(currentValue, nextPlaceValueOf2)
                    if (Math.abs(currentValue) > maxOfPrevious || distance1 < distanceNextDigit || distance1 == nextPlaceValueOf2) {
                        sb.append(positive ? 1 : "-")
                        currentValue -= positive ? valueOf1 : -valueOf1
                    } else {
                        sb.append(0)
                    }
                }
            }
        }

        // Remove any starting 0's
        while (sb.startsWithAny("0")) sb.deleteCharAt(0)

        return sb.toString()
    }

    def getDistance(def a, def b) {
        return Math.abs(Math.abs(a) - b)
    }

    enum Digit {
        TWO("2", 2),
        ONE("1", 1),
        ZERO("0", 0),
        MINUS("-", -1),
        DOUBLE_MINUS("=",-2)

        static def NUMERAL_MAP = [:]

        static {
            for (Digit digit : values()) {
                NUMERAL_MAP[digit.numeral] = digit
            }
        }

        private final String numeral
        private final long value

        Digit(String numeral, int value) {
            this.value = value
            this.numeral = numeral
        }

        static Digit fromNumeral(String numeral) {
            return NUMERAL_MAP[numeral]
        }
    }

    class Place {
        final int powerOf5
        final def valueOf1
        final def valueOf2
        final def maxOfPreviousPlaces

        Place(int powerOf5, def maxOfPreviousPlaces) {
            this.powerOf5 = powerOf5
            this.maxOfPreviousPlaces = maxOfPreviousPlaces
            valueOf1 = Math.pow(5, powerOf5)
            valueOf2 = valueOf1 * 2
        }
    }
}
