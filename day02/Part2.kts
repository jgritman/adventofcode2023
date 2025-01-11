import java.io.File

val fileName = args[0]

fun gamePower(line: String): Int {
    return line.substringAfter(":")
        .split("[;,]".toRegex())
        .map { blockCount ->
            val (countStr, color) = blockCount.trim().split(" ")
            color to countStr.toInt()
        }
        .fold(mutableMapOf<String, Int>()) { maximums, (color, count) ->
            maximums.merge(color, count) { oldValue, newValue -> maxOf(oldValue, newValue) }
            maximums
        }
        .values
        .reduce { acc, value -> acc * value }
}

val sum = File(fileName).useLines { lines ->
    lines.sumOf(::gamePower)
}

println("Total sum: $sum")
