package problems0920

fun main() = generateSequence(::readlnOrNull)
    .takeWhile { it.isNotBlank() }
    .map { it.trim()}
    .map { line ->
        line.split(' ')
            .let { parts ->
                if (parts.size < 3
                    || parts[1].toIntOrNull() != null
                    || parts.subList(2, parts.size).any { it.toIntOrNull() == null }
                    || !parts.subList(2, parts.size).all { grade -> grade.all(Char::isDigit) }) {
                    println("INVALID INPUT")
                    null
                } else {
                        Triple(
                            "${parts[0]} ${parts[1]}",
                            parts.subList(2, parts.size).map { it.toInt() }
                                .sorted().let { sortedGrades ->
                                    sortedGrades[sortedGrades.size / 2].toDouble().takeIf { sortedGrades.size % 2 == 1 }
                                        ?: ((sortedGrades[sortedGrades.size / 2 - 1] + sortedGrades[sortedGrades.size / 2]) / 2.0)
                                },
                            parts.drop(2).map(String::toInt)
                        )
                    }
            }
    }
    .filterNotNull()
    .toList()
    .let { students ->
        students
            .groupBy { it.second }
            .toList()
            .sortedByDescending { it.first }
            .take(3)
            .flatMap { it.second }
            .sortedWith { o1, o2 -> o1.first.compareTo(o2.first) }
//            .take(3)
    }
    .forEach {(name, median, grades) ->
        println("\n$name \n$median \n${grades.joinToString(" ")}")
    }
