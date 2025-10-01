package problems0920

fun main() = println(readlnOrNull()
    ?.split(' ')
    ?.mapNotNull { it.toIntOrNull()
        ?.takeIf { num -> num >= 0 }
    }
    ?.count { it.toString()
        .toSet().size == it.toString().length })