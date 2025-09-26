package problems0920

fun main() = println(readlnOrNull()?.toIntOrNull()
    ?.takeIf { it >= 0 }?.toString()
    ?.map { it.digitToInt() }
    ?.filter { it % 3 == 0 }
    ?.maxOrNull() ?: -1)
