package problems0920

fun main() = println(generateSequence(1) { it + 1 }.flatMap { c ->
    (1 until c).flatMap { a -> (a until c).map { b -> Triple(a, b, c) } }
}.filter { it.first * it.first + it.second * it.second == it.third * it.third }
    .map { it.first + it.second + it.third }.let { sums ->
        readln().toInt().let { n -> sums.filter { it > n }.minByOrNull { it - n } ?: sums.maxOrNull() }
    })