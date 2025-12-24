package `2025`

import println
import readInput

fun main() {
    fun parse(input: List<String>): List<LongRange> {
        check(input.size == 1)
        return input.first().split(",").map {
            it.split("-").let { (start, end) ->
                LongRange(start.toLong(), end.toLong())
            }
        }
    }

    fun isValidPart1(id: Long): Boolean {
        val idAsString = id.toString()
        return if (idAsString.length.mod(2) == 1) {
            true
        } else {
            idAsString.substring(0, idAsString.length / 2) != idAsString.substring(idAsString.length / 2)
        }
    }

    fun part1(input: List<String>): Long {
        val ranges = parse(input)

        return ranges.sumOf { range ->
            range.sumOf { id ->
                if (isValidPart1(id)) {
                    0
                } else {
                    id
                }
            }
        }
    }

    fun isValidPart2(id: Long): Boolean {
        val idAsString = id.toString()
        return (2..idAsString.length).all { numberOfRepeats ->
            if (idAsString.length.mod(numberOfRepeats) != 0) {
                true
            } else {
                idAsString.chunked(idAsString.length / numberOfRepeats).toSet().size != 1
            }
        }
    }

    fun part2(input: List<String>): Long {
        val ranges = parse(input)

        return ranges.sumOf { range ->
            range.sumOf { id ->
                if (isValidPart2(id)) {
                    0
                } else {
                    id
                }
            }
        }
    }

    // Or read a large test input from the `src/DayXX_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)

    // Read the input from the `src/DayXX.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
