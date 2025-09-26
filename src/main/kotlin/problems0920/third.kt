package problems0920

fun main() = println(('a'..'z')
    .filter { c -> readlnOrNull()
        ?.split(' ')
        ?.count { w -> c !in w } == 2 }.joinToString(" "))