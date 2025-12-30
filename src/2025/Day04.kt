package `2025`

import androidx.compose.ui.unit.IntOffset
import println

fun main() {
    fun parsePaperLocations(input: List<String>): Set<IntOffset> =
        buildSet {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    if (char == '@') {
                        add(IntOffset(x, y))
                    }
                }
            }
        }

    fun part1(input: List<String>): Int {
        val paperLocations = parsePaperLocations(input)
        val rows = input.size
        val columns = input.first().length

        var accessible = 0
        (0 until rows).forEach { y ->
            (0 until columns).forEach { x ->
                if (IntOffset(x, y) in paperLocations) {
                    val neighboringPapers: Int = listOf(
                        IntOffset(x - 1, y - 1),
                        IntOffset(x, y - 1),
                        IntOffset(x + 1, y - 1),
                        IntOffset(x - 1, y),
                        IntOffset(x + 1, y),
                        IntOffset(x - 1, y + 1),
                        IntOffset(x, y + 1),
                        IntOffset(x + 1, y + 1),
                    ).sumOf {
                        if (it in paperLocations) {
                            1
                        } else {
                            0
                        }
                    }

                    if (neighboringPapers < 4) {
                        accessible++
                    }
                }
            }
        }

        return accessible
    }

    fun part2(input: List<String>): Int {
        var paperLocations = parsePaperLocations(input)
        val rows = input.size
        val columns = input.first().length

        var removed = 0
        var nextPaperLocations = paperLocations

        do {
            paperLocations = nextPaperLocations
            nextPaperLocations = paperLocations.toMutableSet()
            (0 until rows).forEach { y ->
                (0 until columns).forEach { x ->
                    if (IntOffset(x, y) in paperLocations) {
                        val neighboringPapers = listOf(
                            IntOffset(x - 1, y - 1),
                            IntOffset(x, y - 1),
                            IntOffset(x + 1, y - 1),
                            IntOffset(x - 1, y),
                            IntOffset(x + 1, y),
                            IntOffset(x - 1, y + 1),
                            IntOffset(x, y + 1),
                            IntOffset(x + 1, y + 1),
                        ).sumOf {
                            if (it in paperLocations) {
                                1
                            } else {
                                0
                            }
                        }

                        if (neighboringPapers < 4) {
                            removed++
                            nextPaperLocations.remove(IntOffset(x, y))
                        }
                    }
                }
            }
        } while (nextPaperLocations != paperLocations)

        return removed
    }

    // Or read a large test input from the `src/DayXX_test.txt` file:
    val testInput = readInput("Day04_test")
    val testInputPart1 = part1(testInput)
    check(testInputPart1 == 13) {
        "Test input value $testInputPart1 was incorrect"
    }
    val testInputPart2 = part2(testInput)
    check(testInputPart2 == 43) {
        "Test input value $testInputPart2 was incorrect"
    }

    // Read the input from the `src/DayXX.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
