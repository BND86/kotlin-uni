package problems0920

fun main() = generateSequence { readlnOrNull() }.takeWhile { it.isNotBlank() }.map { line ->
    val parts = line.split(' ')
    Triple(parts[0], parts[1], parts.drop(2).map(String::toInt).sorted().let { it ->
        it[it.size / 2].toDouble().takeIf { it.size % 2 != 0 } ?: ((it[it.size / 2 - 1] + it[it.size / 2]) / 2.0)
    })
}.toList().let { students ->
    students.groupBy { it.third }.toList().sortedByDescending { it.first }.take(3).flatMap { it.second.sortedWith(compareBy({ it.first }, { it.second })) }
}.forEach { println("${it.first} ${it.second} ${it.third}") }