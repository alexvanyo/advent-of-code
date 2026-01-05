package `2025`

import androidx.compose.ui.unit.IntOffset
import getMooreNeighbors
import println
import java.util.PriorityQueue

private data class IngredientInput(
    val ranges: List<LongRange>,
    val availableIngredients: List<Long>,
)

data class RangeEdge(
    val id: Long,
    val isStart: Boolean
) : Comparable<RangeEdge> {
    override fun compareTo(other: RangeEdge): Int =
        if (isStart == other.isStart) {
            id.compareTo(other.id)
        } else if (isStart) {
            -1
        } else {
            1
        }
}

fun main() {
    fun parse(input: List<String>): IngredientInput {
        val emptyLineIndex = input.indexOf("")
        val ranges = input.subList(0, emptyLineIndex).map {
            it.split("-").map { it.toLong() }.let { (a, b) -> a..b }
        }
        val ids = input.subList(emptyLineIndex + 1, input.size).map { it.toLong() }
        return IngredientInput(ranges, ids)
    }

    fun part1(input: List<String>): Int {
        val ingredientInput = parse(input)
        return ingredientInput.availableIngredients.count { id ->
            ingredientInput.ranges.any { range ->
                id in range
            }
        }
    }

    fun part2(input: List<String>): Long {
        val ingredientInput = parse(input)
        val rangeEdges = ingredientInput.ranges.flatMap {
            listOf(
                RangeEdge(
                    it.first,
                    true,
                ),
                RangeEdge(
                    it.last,
                    false
                )
            )

        }.also { it.println() }
        val queue = PriorityQueue<RangeEdge>(rangeEdges).also { it.toList().println() }
        var validCount = 0L
        var rangeStarts = 0L
        var firstStart: Long? = null
        while (queue.isNotEmpty()) {
            val edge = queue.remove()
            if (edge.isStart) {
                if (firstStart == null) {
                    firstStart = edge.id
                }
                rangeStarts++
            } else {
                rangeStarts--
                check(rangeStarts >= 0L)
                if (rangeStarts == 0L) {
                    validCount += edge.id - firstStart!! + 1
                    firstStart = null
                }
            }
        }
        return validCount
    }

    // Or read a large test input from the `src/DayXX_test.txt` file:
    val testInput = readInput("Day05_test")
    val testInputPart1 = part1(testInput)
    check(testInputPart1 == 3) {
        "Test input value $testInputPart1 was incorrect"
    }
    val testInputPart2 = part2(testInput)
    check(testInputPart2 == 14L) {
        "Test input value $testInputPart2 was incorrect"
    }

    // Read the input from the `src/DayXX.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}


