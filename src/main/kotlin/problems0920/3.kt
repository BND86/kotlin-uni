package problems0920

fun main() = println(readlnOrNull()
    ?.split(' ')
    ?.map { word ->
        word.toIntOrNull()?.takeIf { num -> num >= 0 }
            ?: return println("INVALID INPUT")
    }
    ?.count { it.toString()
        .toSet().size == it.toString().length } ?: "EMPTY INPUT")