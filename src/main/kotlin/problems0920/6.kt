package problems0920

import kotlin.math.sqrt

fun main() = println(readlnOrNull()?.toIntOrNull()?.let { n ->
    (1..100000).flatMap { a ->
        (a..150000).mapNotNull { b ->
            val c = sqrt((a * a + b * b).toDouble()).toInt()
            if (c * c == a * a + b * b) Triple(a, b, c) else null
        }
    }.map { it to it.first + it.second + it.third }
        .filter { it.second > n }
        .minByOrNull { it.second - n }
        ?.let { (triple, _) -> "${triple.first} ${triple.second} ${triple.third}" }
        ?: "Тройка не найдена"
} ?: "INVALID INPUT")
