import java.io.File

val fileName = args[0]

fun processLine(line: String): Int {
    val first = line.first { it.isDigit() }
    val last = line.last { it.isDigit() }
    return "$first$last".toInt()
}

val sum = File(fileName).useLines { lines ->
    lines.map { line -> processLine(line) }.sum()
}

println("Total sum: $sum")

