import java.io.File

val maximums = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)

val fileName = args[0]

fun isValidBlockCount(blockCount: String): Boolean {
    val (countStr, color) = blockCount.trim().split(" ")
    return countStr.toInt() <= maximums[color]!!
}

fun isValidBlockString(blocks: String): Boolean {
    return blocks.split(",").all(::isValidBlockCount)
}

fun validGame(line: String): Boolean {
    return line.substringAfter(":").split(";").all(::isValidBlockString)
}

val sum = File(fileName).useLines { lines ->
    lines.withIndex()
        .filter { (_, line) -> validGame(line) } // Filter lines that meet the condition
        .sumOf { (index, _) -> index + 1 } // Add index + 1 for matching lines
}

println("Total sum: $sum")
