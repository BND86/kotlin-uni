package problems0920

fun main() = println(readlnOrNull()
    ?.split(' ')
    ?.mapNotNull { it.toIntOrNull()
        ?.toString()
        ?.firstOrNull()
        ?.digitToInt() }
    ?.reduceOrNull { acc, d -> acc or d } ?: 0)