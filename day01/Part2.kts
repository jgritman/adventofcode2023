import java.io.File

val fileName = args[0]

val numericStrings = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
val numericDigits = (1..9).map { it.toString() }
val allTokens = numericStrings + numericDigits

fun processToken(token: String?): Int {
    // Check if the token is null or if the first character is not a digit
    return if (token?.firstOrNull()?.isDigit() == true) {
        token.toInt()
    } else {
        // Handle the null case by returning a default value or throwing an exception
        numericStrings.indexOf(token) + 1
    }
}

fun processLine(line: String): Int {
    val first = processToken(line.findAnyOf(allTokens)?.second)
    val last = processToken(line.findLastAnyOf(allTokens)?.second)
    return "$first$last".toInt()
}

// Use lazy sequence to compute the sum
val sum = File(fileName).useLines { lines ->
    lines.map { line -> processLine(line) }.sum()
}

println("Total sum: $sum")

