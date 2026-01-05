import androidx.compose.ui.unit.IntOffset
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs

/**
 * Reads lines from the given input txt file.
 */
fun readInput(year: Int, name: String) = Path("src/$year/$name.txt").readText().trim().lines()

fun String.parseIntPair(): Pair<Int, Int> {
    val (a, b) = split(Regex("\\s+"))
    return a.toInt() to b.toInt()
}

fun String.parseIntList(): List<Int> =
    split(Regex("\\s+")).map(String::toInt)

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun <T> List<T>.removeIndices(vararg indices: Int): List<T> =
    removeIndices(indices.toSet())

fun <T> List<T>.removeIndices(indices: Set<Int>): List<T> =
    filterIndexed { index, _ -> index !in indices }

sealed interface Direction {
    data object Up : Direction
    data object Down : Direction
    data object Left : Direction
    data object Right : Direction
}

tailrec fun gcd(a: Int, b: Int): Int =
    if (b == 0) abs(a) else gcd(b, a.mod(b))

tailrec fun gcd(a: Long, b: Long): Long =
    if (b == 0L) abs(a) else gcd(b, a.mod(b))

val IntOffset.manhattanDistance get() = abs(x) + abs(y)

/**
 * Returns the 8 diagonal and orthogonal neighbors to the [IntOffset].
 */
fun IntOffset.getMooreNeighbors(): Set<IntOffset> = mooreNeighborOffsets.map { it + this }.toSet()

private val mooreNeighborOffsets = listOf(
    IntOffset(-1, -1),
    IntOffset(0, -1),
    IntOffset(1, -1),
    IntOffset(-1, 0),
    IntOffset(1, 0),
    IntOffset(-1, 1),
    IntOffset(0, 1),
    IntOffset(1, 1),
)

/**
 * Returns the 4 orthogonal neighbors to the [IntOffset].
 */
fun IntOffset.getVonNeumannNeighbors(): Set<IntOffset> = vonNeumannNeighborOffsets.map { it + this }.toSet()

private val vonNeumannNeighborOffsets = listOf(
    IntOffset(0, -1),
    IntOffset(-1, 0),
    IntOffset(1, 0),
    IntOffset(0, 1),
)

public fun Iterable<Long>.product(): Long {
    var product: Long = 1L
    for (element in this) {
        product *= element
    }
    return product
}
