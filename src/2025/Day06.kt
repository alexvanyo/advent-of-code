package `2025`

import println
import product
import java.util.PriorityQueue
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.pow

private enum class Operation {
    Addition, Multiplication
}

private data class Problem(
    val numbers: List<Long>,
    val operation: Operation,
)

private data class Worksheet(
    val problems: List<Problem>,
)

fun main() {
    fun parse1(input: List<String>): Worksheet {
        val numberLines = input.dropLast(1).map {
            it.trim().split(Regex("\\s+")).map { it.toLong() }
        }
        val operations = input.last().trim().split(Regex("\\s+")).map {
            when (it) {
                "+" -> Operation.Addition
                "*" -> Operation.Multiplication
                else -> error("Unexpected operation: $it")
            }
        }
        return Worksheet(operations.mapIndexed { index, operation ->
            Problem(
                numbers = numberLines.map { it[index] },
                operation = operation,
            )
        })
    }

    fun part1(input: List<String>): Long {
        val worksheet = parse1(input)
        return worksheet.problems.sumOf {
            when (it.operation) {
                Operation.Addition -> it.numbers.sum()
                Operation.Multiplication -> it.numbers.product()
            }
        }
    }

    fun parse2(input: List<String>): Worksheet {
        val numberLines = input.dropLast(1)

        return Worksheet(
            buildList {
                var start = 0
                (0..input.first().length).forEach { index ->
                    if (index == input.first().length || input.all { it[index] == ' ' }) {
                        val operation = input.last().substring(start, index).trim().let {
                            when (it) {
                                "+" -> Operation.Addition
                                "*" -> Operation.Multiplication
                                else -> error("Unexpected operation: $it")
                            }
                        }

                        val rawNumbers = numberLines.map {
                            it.substring(start, index)
                        }
                        add(
                            Problem(
                                numbers = (0 until index - start).map { numberIndex ->
                                    rawNumbers.mapNotNull { string ->
                                        string.reversed().get(numberIndex).digitToIntOrNull()
                                    }.joinToString("").toLong()
                                },
                                operation = operation,
                            )
                        )
                        start = index + 1
                    }
                }
            }
        )
    }

    fun part2(input: List<String>): Long {
        val worksheet = parse2(input)
        return worksheet.problems.sumOf {
            when (it.operation) {
                Operation.Addition -> it.numbers.sum()
                Operation.Multiplication -> it.numbers.product()
            }
        }
    }


    // Or read a large test input from the `src/DayXX_test.txt` file:
    val testInput = Path("src/2025/Day06_test.txt").readText().lines()
    val testInputPart1 = part1(testInput)
    check(testInputPart1 == 4277556L) {
        "Test input value $testInputPart1 was incorrect"
    }
    val testInputPart2 = part2(testInput)
    check(testInputPart2 == 3263827L) {
        "Test input value $testInputPart2 was incorrect"
    }

    // Read the input from the `src/DayXX.txt` file.
    val input = Path("src/2025/Day06.txt").readText().lines()
    part1(input).println()
    part2(input).println()
}


