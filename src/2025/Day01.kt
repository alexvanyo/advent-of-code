package `2025`

import println
import readInput
import kotlin.math.abs
import kotlin.math.floor

private data class LockRotation(
    val isTowardsBigger: Boolean,
    val clicks: Int,
)

fun main() {
    fun parse(input: List<String>): List<LockRotation> =
        input.map {
            LockRotation(
                isTowardsBigger = it.first() == 'R',
                clicks = it.substring(1).toInt(),
            )
        }

    fun part1(input: List<String>): Int {
        val rotations = parse(input)
        var number = 50
        var timesAtZero = 0

        rotations.forEach {
            number += if (it.isTowardsBigger) it.clicks else -it.clicks
            number = number.mod(100)
            if (number == 0) timesAtZero++
        }

        return timesAtZero
    }

    fun part2(input: List<String>): Int {
        val rotations = parse(input)
        var number = 50
        var timesPassingZero = 0

        rotations.forEach {
            val before = number
            val after = number + if (it.isTowardsBigger) it.clicks else -it.clicks

            if (after == 0) {
                if (before != 0) {
                    timesPassingZero++
                }
            } else if (after < 0) {
                timesPassingZero += abs(floor((after - 1) / 100f).toInt())
                if (before == 0) {
                    timesPassingZero--
                }
            } else if (after >= 100) {
                timesPassingZero += after / 100
            }

            number = after.mod(100)
            check(number in 0..<100)
        }

        return timesPassingZero
    }

    val testInput = readInput("Day01_test")
    check(part2(testInput) == 6)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
