fun main() {
    val input = readlnOrNull()?.trim()?.trimStart('0')?.ifEmpty { "0" }?.toIntOrNull()
    if (input == null) {
        println("Invalid input")
    }
    else {
        val func1: (Int) -> Int? = { if (it > 0) it * 2 else null }
        val func2: (Int) -> Int? = { if (it < 10) it * 3 else null }

        val maxFunction = createMaxFunction(func1, func2)

        println("result = ${maxFunction(input)}")
    }
}

fun createMaxFunction(func1: (Int) -> Int?, func2: (Int) -> Int?): (Int) -> Int? {
    return { x ->
        val result1 = func1(x)
        val result2 = func2(x)

        if (result1 == null || result2 == null) {
            null
        } else {
            maxOf(result1, result2)
        }
    }
}
