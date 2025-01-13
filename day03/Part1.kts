import java.io.File

val fileName = args[0]

// all combinations of -1,0,1 exluding 0,0
val directions = (-1..1).flatMap { x ->
    (-1..1).map { y -> x to y }
}.filter { it != 0 to 0 }

val symbolRegex = "[^a-zA-Z0-9.]".toRegex()

fun processRow(rowIndex: Int, matrix: List<CharArray>): Int {
    val row = matrix[rowIndex]
    var rowSum = 0
    var digitBuffer = mutableListOf<Char>()
    var hasValidDigit = false

    row.forEachIndexed { colIndex, colValue ->
        when {
            colValue.isDigit() -> {
                digitBuffer.add(colValue)
                if (!hasValidDigit && hasAdjacentSymbol(rowIndex, colIndex, matrix)) {
                    hasValidDigit = true
                }
            }
            digitBuffer.isNotEmpty() -> {
                if (hasValidDigit) {
                    rowSum += extractNumber(digitBuffer)
                    hasValidDigit = false
                }
                digitBuffer.clear()
            }
        }
    }

    if (hasValidDigit) {
        rowSum += extractNumber(digitBuffer)
    }

    return rowSum
}

fun hasAdjacentSymbol(rowIndex: Int, colIndex: Int, matrix: List<CharArray>): Boolean {
    return directions.any { (deltaRow, deltaCol) ->
        val newRow = rowIndex + deltaRow
        val newCol = colIndex + deltaCol
        isValidIndex(newRow, newCol, matrix) &&
            matrix[newRow][newCol].toString().matches(symbolRegex)
    }
}

fun isValidIndex(row: Int, col: Int, matrix: List<CharArray>): Boolean {
    return row in matrix.indices && col in matrix[row].indices
}

fun extractNumber(digitChars: List<Char>): Int {
    return digitChars.joinToString("").toInt()
}

val sumOfValid = File(fileName).useLines { lines ->
    val matrix = lines.map { it.toCharArray() }.toList()
    matrix.withIndex().sumOf { (rowIndex, _) -> processRow(rowIndex, matrix) }
}

println("Total sum: $sumOfValid")
