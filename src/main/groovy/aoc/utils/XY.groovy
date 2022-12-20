package aoc.utils

/**
 * Object to represent (x,y) coordinate.
 */
class XY {
    int x
    int y

    XY(int x, int y) {
        this.x = x
        this.y = y
    }

    XY(XY original)
    {
        x = original.x
        y = original.y
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        XY xy = (XY) o

        if (x != xy.x) return false
        if (y != xy.y) return false

        return true
    }

    int hashCode() {
        int result
        result = x
        result = 31 * result + y
        return result
    }
}
