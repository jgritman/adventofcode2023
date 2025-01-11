import java.io.File

val fileName = args[0]

// all combinations of -1,0,1 exluding 0,0
val directions = (-1..1).flatMap { x ->
    (-1..1).map { y -> x to y }
}.filter { it != 0 to 0 }

val gearChar = '*'

fun processRow(rowIndex: Int, matrix: List<CharArray>, gearMap: MutableMap<Pair<Int,Int>, MutableList<Int>>) {
    val row = matrix[rowIndex]
    var digitBuffer = mutableListOf<Char>()
    var gearBuffer = mutableSetOf<Pair<Int, Int>>()

    row.forEachIndexed { colIndex, colValue ->
        if (colValue.isDigit()) {
            digitBuffer.add(colValue)
            gearBuffer.addAll(findAdjacentGears(rowIndex, colIndex, matrix))
        } else {
            processDigit(digitBuffer, gearBuffer, gearMap)
        }
    }
    processDigit(digitBuffer, gearBuffer, gearMap)
}

fun processDigit(digitBuffer: MutableList<Char>, gearBuffer: MutableSet<Pair<Int, Int>>, gearMap: MutableMap<Pair<Int,Int>, MutableList<Int>>) {
    if (digitBuffer.isNotEmpty()) {
        val number = extractNumber(digitBuffer)
        gearBuffer.forEach { gear ->
            var numberList = gearMap.getOrPut(gear) { mutableListOf() }
            numberList.add(number)
        }
        digitBuffer.clear()
        gearBuffer.clear()
    }
}

fun findAdjacentGears(rowIndex: Int, colIndex: Int, matrix: List<CharArray>): List<Pair<Int, Int>> {
    return directions
        .map { Pair(it.first + rowIndex, it.second + colIndex) }
        .filter { (newRow, newCol) -> isValidIndex(newRow, newCol, matrix) && matrix[newRow][newCol] == gearChar }
}

fun isValidIndex(row: Int, col: Int, matrix: List<CharArray>): Boolean {
    return row in matrix.indices && col in matrix[row].indices
}

fun extractNumber(digitChars: List<Char>): Int {
    return digitChars.joinToString("").toInt()
}

val sum = File(fileName).useLines { lines ->
    val matrix = lines.map { it.toCharArray() }.toList() // Convert Sequence<CharArray> to List<CharArray>
    val gearMap = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()

    matrix.forEachIndexed { rowIndex, _ -> processRow(rowIndex, matrix, gearMap) }

    gearMap.values.filter { it.size == 2 }.sumOf { it[0] * it[1] }
}

println("Total sum: $sum")
