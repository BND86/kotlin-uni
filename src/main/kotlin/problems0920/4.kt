package problems0920

fun main() = println(readlnOrNull()
    ?.split(' ')
    ?.let { list ->
        if (list.any { it.toIntOrNull() == null }) "INVALID INPUT" else
            list.map {
                    it.first()
                    .digitToInt() }
                .reduceOrNull { acc, d -> acc or d } ?: 0 } ?: "EMPTY INPUT")