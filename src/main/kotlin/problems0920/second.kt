package problems0920

fun main() = println(readlnOrNull()
    ?.split(' ')
    ?.filter { it.length % 2 == 0 }
    ?.minByOrNull { it.length }
    ?.firstOrNull() ?: "null")
