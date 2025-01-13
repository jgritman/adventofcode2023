import java.io.File

val fileName = args[0]

fun processCard(line: String, gameIndex: Int, cardCounts: MutableMap<Int, Int>): Int {
    val (winnersString, ticketString) = line.substringAfter(":").split("|")
    val winningNumbers = parseNumberString(winnersString).toSet()
    val currentCardCount = 1 + (cardCounts.getOrDefault(gameIndex, 0))

    val winningCount = parseNumberString(ticketString).count { it in winningNumbers }

    (1..winningCount).forEach { i ->
        val incrementIndex = gameIndex + i
        cardCounts.merge(incrementIndex, currentCardCount) { old, new -> old + new }
    }

    return currentCardCount
}

fun parseNumberString(numberString: String): List<Int> {
    return numberString.trim()
        .split("\\s+".toRegex())
        .map(String::toInt)
}

val sum = File(fileName).useLines { lines ->
    val cardCounts = mutableMapOf<Int, Int>()
    lines.withIndex().sumOf { (index, line) -> processCard(line, index, cardCounts) }

}

println("Total sum: $sum")
