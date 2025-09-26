fun main() {
    val input = readlnOrNull()?.trim()?.trimStart('0')?.ifEmpty { "0" }

    if (!input?.all { it.isDigit() }!!) {
        println("Invalid input")
    }
    else {
        var n = input.toIntOrNull()
        if (n == 0) {
            println("0")
        }
        else {
            var maxDigit: Int? = null

            if (n != null) {
                while (n > 0) {
                    val digit = n % 10
                    if (digit % 3 == 0) {
                        if (maxDigit == null || digit > maxDigit) {
                            maxDigit = digit
                        }
                    }
                    n /= 10
                }
            }
            println(maxDigit)
        }
    }
}
