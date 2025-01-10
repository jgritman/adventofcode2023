import java.io.File

val fileName = args[0]

fun gamePower(line: String): Int {
    val blockCounts = line.substringAfter(":").split("[;,]".toRegex())
    var maximums = mutableMapOf<String, Int>()
    blockCounts.forEach { blockCount ->
        val (countStr, color) = blockCount.trim().split(" ")

        // Update the map to keep the maximum value for each color
        maximums.merge(color, countStr.toInt()) { oldValue, newValue -> maxOf(oldValue, newValue) }
    }
    return maximums.values.reduce { acc, value -> acc * value }
}

val sum = File(fileName).useLines { lines ->
    lines.sumOf(::gamePower)
}

println("Total sum: $sum")
