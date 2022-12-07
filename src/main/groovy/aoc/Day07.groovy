package aoc
/**
 * Day 7: No Space Left On Device
 *
 * @see <ahref="https://adventofcode.com/2022/day/7"   >   AOC 2022 Day 7</a>
 */
class Day07 {
    static final int TOTAL = 70_000_000
    static final int TOTAL_SPACE_NEEDED = 30_000_000
    def bigDirSum = 0
    List<Integer> dirSizes = []

    int findDirectorySize(String fileName, boolean part1) {
        def root = new MyFile("/", true, null)
        MyFile currentDirectory = root

        // Construct file tree
        getClass().getClassLoader().getResource(fileName).splitEachLine(" ", params -> {
            if (params[0] == "\$") {
                def action = params[1]

                if (action == "cd") {
                    def destination = params[2]
                    if (destination == "/") {
                        currentDirectory = root
                    } else if (destination == "..") {
                        currentDirectory = currentDirectory.parent
                    } else {
                        currentDirectory = currentDirectory.contents.stream().filter(f -> f.name == destination).findFirst().orElse(null)
                    }

//                    println "currentDirectory: $currentDirectory"
                }
            } else {
                if (params[0] == "dir") // Add directory to current dir
                {
                    def newFile = new MyFile(params[1], true, currentDirectory)
                    currentDirectory.contents << newFile
//                    println "Adding: $newFile"
                } else { // Add file to current dir
                    def newFile = new MyFile(params[1], params[0] as int, currentDirectory)
                    currentDirectory.contents << newFile
//                    println "Adding: $newFile"
                }
            }
        })

        // Calculate directory sizes and sum directories larger than 100_000
        calculateDirectorySize(root)

        if (part1) return bigDirSum

        dirSizes.sort()
        def unusedSpace = TOTAL - dirSizes.last()
        def spacedNeeded = TOTAL_SPACE_NEEDED - unusedSpace
        return dirSizes[dirSizes.findIndexOf {it -> it >= spacedNeeded}]
    }

    void calculateDirectorySize(MyFile dir) {
        dir.contents.stream().filter(f -> f.isDir).forEach(f -> calculateDirectorySize(f))
        dir.size = dir.contents.stream().mapToInt(f -> f.size).sum()
        if (dir.size <= 100_000) bigDirSum += dir.size
        dirSizes << dir.size
//        println "dir: ${dir.name}, size: ${dir.size}, bigDirSum: $bigDirSum"
    }

    private class MyFile {
        String name
        int size
        boolean isDir = false
        List<MyFile> contents = []
        MyFile parent

        MyFile(String name, boolean isDir, MyFile parent) {
            this.name = name
            this.isDir = isDir
            this.parent = parent
        }

        MyFile(String name, int size, MyFile parent) {
            this.name = name
            this.size = size
            this.parent = parent
        }

        @Override
        String toString() {
            return "MyFile{" +
                    "name='" + name + '\'' +
                    ", size=" + size +
                    ", isDir=" + isDir +
                    ", parent=" + (parent == null ? parent : parent.name) +
                    '}'
        }
    }
}
