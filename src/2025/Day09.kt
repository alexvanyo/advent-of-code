package `2025`

import Direction
import androidx.compose.ui.unit.IntOffset
import getVonNeumannNeighbors
import parseIntPair
import println
import java.util.PriorityQueue
import kotlin.collections.flatMapIndexed
import kotlin.collections.forEach
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private data class CornerLine(
    val start: IntOffset,
    val end: IntOffset,
    val direction: Direction,
)

private fun convertToLine(start: IntOffset, end: IntOffset) = CornerLine(
    start,
    end,
    if (start.x == end.x) {
        if (start.y < end.y) {
            Direction.Down
        } else {
            Direction.Up
        }
    } else {
        check(start.y == end.y) { "Expected one coordinate pair to be equal" }
        if (start.x < end.x) {
            Direction.Right
        } else {
            Direction.Left
        }
    }
)

fun main() {
    fun part1(input: List<String>): Long {
        val corners = input.map {
            it.split(",").map { it.toInt() }.let { (x, y) -> IntOffset(x, y) }
        }
        return corners
            .flatMapIndexed { index, a ->
                corners.subList(index + 1, corners.size).map { b -> a to b }
            }
            .maxOf { (a, b) ->
                (abs(a.x - b.x) + 1L) * (abs(a.y - b.y) + 1L)
            }

    }


    fun part2(input: List<String>): Long {
        val corners = input.map {
            it.split(",").map { it.toInt() }.let { (x, y) -> IntOffset(x, y) }
        }
        val redTiles = corners.toSet()


        val lines = (corners + corners.first()).zipWithNext().map { (a, b) ->
            convertToLine(a, b)
        }

        var totalAngle = 0

        val lineGreenTiles = buildSet {
            lines.forEach { line ->
                when (line.direction) {
                    Direction.Down -> {
                        (line.start.y until line.end.y).forEach {
                            val offset = IntOffset(line.start.x, it)
                            check(offset !in this) { "Offset $offset already covered" }
                            add(offset)
                        }
                    }
                    Direction.Up -> {
                        (line.start.y downTo line.end.y + 1).forEach {
                            val offset = IntOffset(line.start.x, it)
                            check(offset !in this) { "Offset $offset already covered" }
                            add(offset)
                        }

                    }
                    Direction.Left -> {
                        (line.start.x downTo line.end.x + 1).forEach {
                            val offset = IntOffset(it, line.start.y)
                            check(offset !in this) { "Offset $offset already covered" }
                            add(offset)
                        }
                    }
                    Direction.Right -> {
                        (line.start.x until line.end.x).forEach {
                            val offset = IntOffset(it, line.start.y)
                            check(offset !in this) { "Offset $offset already covered" }
                            add(offset)
                        }
                    }
                }
            }
        } - redTiles

        (lines + lines.first()).zipWithNext().forEach { (a, b) ->
            totalAngle += when (a.direction) {
                Direction.Down -> when (b.direction) {
                    Direction.Down -> 0
                    Direction.Left -> 90
                    Direction.Right -> -90
                    Direction.Up -> error("Shouldn't happen")
                }
                Direction.Left -> when (b.direction) {
                    Direction.Down -> -90
                    Direction.Left -> 0
                    Direction.Right -> error("Shouldn't happen")
                    Direction.Up -> 90
                }
                Direction.Right -> when (b.direction) {
                    Direction.Down -> 90
                    Direction.Left -> error("Shouldn't happen")
                    Direction.Right -> 0
                    Direction.Up -> -90
                }
                Direction.Up -> when (b.direction) {
                    Direction.Down -> error("Shouldn't happen")
                    Direction.Left -> -90
                    Direction.Right -> 90
                    Direction.Up -> 0
                }
            }
        }

        check(totalAngle == 360 || totalAngle == -360) { "Expected total angle to be 360 or -360, but was $totalAngle" }

        val isClockwise = totalAngle == 360

        val outsideLines = buildSet {
            lines.forEach { line ->
                when (line.direction) {
                    Direction.Down -> {
                        (line.start.y..line.end.y).forEach {
                            val offset = IntOffset(line.start.x + if (isClockwise) 1 else -1, it)
                            add(offset)
                        }
                    }
                    Direction.Up -> {
                        (line.start.y downTo line.end.y).forEach {
                            val offset = IntOffset(line.start.x + if (isClockwise) -1 else 1, it)
                            add(offset)
                        }

                    }
                    Direction.Left -> {
                        (line.start.x downTo line.end.x).forEach {
                            val offset = IntOffset(it, line.start.y + if (isClockwise) 1 else -1)
                            add(offset)
                        }
                    }
                    Direction.Right -> {
                        (line.start.x .. line.end.x).forEach {
                            val offset = IntOffset(it, line.start.y  + if (isClockwise) -1 else 1)
                            add(offset)
                        }
                    }
                }
            }
        } - redTiles - lineGreenTiles

//        (corners.minOf { it.y } - 1..corners.maxOf { it.y } + 1).map { y ->
//            (corners.minOf { it.x } - 1..corners.maxOf { it.x } + 1).map { x ->
//                if (IntOffset(x, y) in lineGreenTiles) {
//                    "X"
//                } else if (IntOffset(x, y) in outsideLines) {
//                    "0"
//                } else if (IntOffset(x, y) in redTiles) {
//                    "#"
//                } else {
//                    "."
//                }
//            }.joinToString("")
//        }.joinToString("\n").println()

        return corners
            .flatMapIndexed { index, a ->
                corners.subList(index + 1, corners.size).map { b -> a to b }
            }
            .map { (a, b) -> Triple(a, b, (abs(a.x - b.x) + 1L) * (abs(a.y - b.y) + 1L)) }
            .sortedByDescending { it.third }
            .first { (a, b, _) ->
                val topLeft = IntOffset(min(a.x, b.x), min(a.y, b.y))
                val bottomRight = IntOffset(max(a.x, b.x), max(a.y, b.y))

                val rectangleLines = listOf(
                    topLeft,
                    IntOffset(bottomRight.x, topLeft.y),
                    bottomRight,
                    IntOffset(topLeft.x, bottomRight.y),
                    topLeft,
                ).zipWithNext().map { (a, b) -> convertToLine(a, b) }

                val isAllInLoop = rectangleLines.all { line ->
                    when (line.direction) {
                        Direction.Down -> {
                            (line.start.y..line.end.y).all {
                                val offset = IntOffset(line.start.x, it)
                                offset !in outsideLines
                            }
                        }
                        Direction.Up -> {
                            (line.start.y downTo line.end.y).all {
                                val offset = IntOffset(line.start.x, it)
                                offset !in outsideLines
                            }

                        }
                        Direction.Left -> {
                            (line.start.x downTo line.end.x).all {
                                val offset = IntOffset(it, line.start.y)
                                offset !in outsideLines
                            }
                        }
                        Direction.Right -> {
                            (line.start.x .. line.end.x).all {
                                val offset = IntOffset(it, line.start.y)
                                offset !in outsideLines
                            }
                        }
                    }
                }

                isAllInLoop
            }
            .third
    }

    // Or read a large test input from the `src/DayXX_test.txt` file:
    val testInput = readInput("Day09_test")
    val testInputPart1 = part1(testInput)
    check(testInputPart1 == 50L) {
        "Test input value $testInputPart1 was incorrect"
    }
    val testInputPart2 = part2(testInput)
    check(testInputPart2 == 24L) {
        "Test input value $testInputPart2 was incorrect"
    }

    // Read the input from the `src/DayXX.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}


