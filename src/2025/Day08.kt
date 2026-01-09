package `2025`

import androidx.compose.ui.unit.IntOffset
import println
import java.util.PriorityQueue

private data class JunctionBox(
    val id: Int,
    val x: Long,
    val y: Long,
    val z: Long,
)

fun main() {
    fun parse(input: List<String>): List<JunctionBox> =
        input.mapIndexed { index, line ->
            line.split(",").map { it.toLong() }.let { (x, y, z) -> JunctionBox(index, x, y, z) }
        }

    fun part1(input: List<String>, pairs: Int): Long {
        val boxes = parse(input)
        val queue = PriorityQueue<Pair<JunctionBox, JunctionBox>>(
            compareBy { (a, b) ->
                (a.x - b.x) * (a.x - b.x) +
                    (a.y - b.y) * (a.y - b.y) +
                    (a.z - b.z) * (a.z - b.z)
            }
        )
        queue.addAll(boxes.flatMapIndexed { index, a ->
            boxes.subList(index + 1, boxes.size).map { b -> a to b }
        })
        val circuitIds = mutableMapOf<Int, Set<Int>>()
        boxes.forEach {
            circuitIds[it.id] = setOf(it.id)
        }

        repeat(pairs) {
            val (a, b) = queue.remove()
            if (circuitIds[a.id] != circuitIds[b.id]) {
                val aCircuit = circuitIds[a.id]!!
                val bCircuit = circuitIds[b.id]!!
                val newCircuit = aCircuit + bCircuit
                newCircuit.forEach { circuitIds[it] = newCircuit }
            }
        }

        val largestCircuits = circuitIds.values.toSet().sortedByDescending { it.size }
        return largestCircuits[0].size.toLong() * largestCircuits[1].size.toLong() * largestCircuits[2].size.toLong()
    }

    fun part2(input: List<String>): Long {
        val boxes = parse(input)
        val queue = PriorityQueue<Pair<JunctionBox, JunctionBox>>(
            compareBy { (a, b) ->
                (a.x - b.x) * (a.x - b.x) +
                        (a.y - b.y) * (a.y - b.y) +
                        (a.z - b.z) * (a.z - b.z)
            }
        )
        queue.addAll(boxes.flatMapIndexed { index, a ->
            boxes.subList(index + 1, boxes.size).map { b -> a to b }
        })
        val circuitIds = mutableMapOf<Int, Set<Int>>()
        boxes.forEach {
            circuitIds[it.id] = setOf(it.id)
        }

        while (true) {
            val (a, b) = queue.remove()
            if (circuitIds[a.id] != circuitIds[b.id]) {
                val aCircuit = circuitIds[a.id]!!
                val bCircuit = circuitIds[b.id]!!
                val newCircuit = aCircuit + bCircuit
                if (newCircuit.size == boxes.size) {
                    return a.x * b.x
                }
                newCircuit.forEach { circuitIds[it] = newCircuit }
            }
        }
    }

    // Or read a large test input from the `src/DayXX_test.txt` file:
    val testInput = readInput("Day08_test")
    val testInputPart1 = part1(testInput, 10)
    check(testInputPart1 == 40L) {
        "Test input value $testInputPart1 was incorrect"
    }
    val testInputPart2 = part2(testInput)
    check(testInputPart2 == 25272L) {
        "Test input value $testInputPart2 was incorrect"
    }

    // Read the input from the `src/DayXX.txt` file.
    val input = readInput("Day08")
    part1(input, 1000).println()
    part2(input).println()
}


