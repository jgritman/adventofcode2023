import java.io.File

val fileName = args[0]

fun cardScore(line: String): Int {
    val (winnersString, ticketString) = line.substringAfter(":").split("|")
    val winningNumbers = parseNumberString(winnersString).toSet()
    return parseNumberString(ticketString)
        .count { it in winningNumbers }
        .takeIf { it > 0 }?.let { 1 shl (it - 1) } ?: 0
}

fun parseNumberString(numberString: String): List<Int> {
    return numberString.trim()
        .split("\\s+".toRegex())
        .map(String::toInt)
}

val sum = File(fileName).useLines { lines ->
    lines.map { line -> cardScore(line) }.sum()
}

println("Total sum: $sum")
