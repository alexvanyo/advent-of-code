package `2025`

import println
import readInput
import kotlin.math.max

fun main() {
    fun calculate(input: List<String>, joltageLength: Int) =
        input.sumOf { line ->
            val digits = line.toCharArray().map(Char::digitToInt).map(Int::toLong)
            val longestOfLength = mutableMapOf<Pair<Int, Int>, Long>()
            (0..digits.size).forEach { i ->
                longestOfLength[0 to i] = 0
            }
            var factor = 1L
            repeat(joltageLength) { length ->
                digits.indices.reversed().forEach { i ->
                    val newValue = max(
                        longestOfLength[length to i + 1]?.let { it + digits[i] * factor } ?: -1,
                        longestOfLength[length + 1 to i + 1] ?: -1
                    )
                    if (newValue >= 0) {
                        longestOfLength[length + 1 to i] = newValue
                    }


                }
                factor *= 10L
            }
            longestOfLength[joltageLength to 0]!!
        }

    fun part1(input: List<String>): Long =
        calculate(input, 2)

    fun part2(input: List<String>): Long =
        calculate(input, 12)

    // Or read a large test input from the `src/DayXX_test.txt` file:
    val testInput = readInput("Day03_test")
    //val testInputPart1 = part1(testInput)
//    check(testInputPart1 == 357L) {
//        "Test input value $testInputPart1 was incorrect"
//    }
    val testInputPart2 = part2(testInput)
    check(testInputPart2 == 3121910778619L) {
        "Test input value $testInputPart2 was incorrect"
    }

    // Read the input from the `src/DayXX.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
