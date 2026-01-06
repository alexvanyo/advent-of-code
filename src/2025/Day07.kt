package `2025`

import androidx.compose.ui.unit.IntOffset
import println
import java.util.PriorityQueue

fun main() {
    fun part1(input: List<String>): Int {
        val startIndex = input.first().indexOf('S')
        val beamIndices = mutableSetOf(startIndex)
        var splitCount = 0
        input.drop(1).forEach { line ->
            line.forEachIndexed { index, char ->
                if (char == '^' && index in beamIndices) {
                    beamIndices.add(index - 1)
                    beamIndices.add(index + 1)
                    beamIndices.remove(index)
                    splitCount++
                }
            }
        }
        return splitCount
    }

    val memoizedPart2 = mutableMapOf<IntOffset, Long>()

    fun part2(input: List<String>, location: IntOffset): Long =
        memoizedPart2.getOrPut(location) {
            if (location.y == input.lastIndex) {
                1L
            } else if (input[location.y][location.x] == '^') {
                part2(input, IntOffset(location.x - 1, location.y)) +
                part2(input, IntOffset(location.x + 1, location.y))
            } else {
                part2(input, IntOffset(location.x, location.y + 1))
            }
        }

    fun part2(input: List<String>): Long =
        part2(input, IntOffset(input.first().indexOf('S'), 0))

    // Or read a large test input from the `src/DayXX_test.txt` file:
    val testInput = readInput("Day07_test")
    val testInputPart1 = part1(testInput)
    check(testInputPart1 == 21) {
        "Test input value $testInputPart1 was incorrect"
    }
    val testInputPart2 = part2(testInput)
    check(testInputPart2 == 40L) {
        "Test input value $testInputPart2 was incorrect"
    }

    // Read the input from the `src/DayXX.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}


