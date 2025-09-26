fun main() {
    val input = readln().trim().trimStart('0').ifEmpty { "0" }
    if (!input.all { it.isDigit() }) {
        println("Invalid input")
    }
    else {
        val multipleOfThree = findDigit(input.toInt()) { it % 3 == 0 }
        println("Max digit: $multipleOfThree")
    }
}

//
fun findDigit(number: Int, condition: (Int) -> Boolean): Int {
    if (number == 0) {
        return 0
    }

    var maxDigit = -1
    var n = number

    while (n > 0) {
        val digit = n % 10
        n /= 10

        if (condition(digit) && digit > maxDigit) {
            maxDigit = digit
        }
    }

    return maxDigit
}