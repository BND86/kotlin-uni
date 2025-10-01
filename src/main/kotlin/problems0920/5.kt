package problems0920

fun main() = generateSequence(::readlnOrNull)
    .takeWhile { it.isNotBlank() }
    .map { it.trim()}
    .map { line ->
        line.split(' ')
            .takeIf { parts ->
                parts.size >= 3
                        && parts.subList(2, parts.size).all { grade -> grade.all(Char::isDigit) }
            }
            ?.let { parts ->
                Triple(
                    "${parts[0]} ${parts[1]}",
                    parts.subList(2, parts.size).map(String::toInt)
                        .sorted().let { sortedGrades ->
                            sortedGrades[sortedGrades.size / 2].toDouble().takeIf { sortedGrades.size % 2 == 1 }
                                ?: ((sortedGrades[sortedGrades.size / 2 - 1] + sortedGrades[sortedGrades.size / 2]) / 2.0)
                        },
                    parts.subList(2, parts.size).map(String::toInt)
                )
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

//asd ghjk 3 4 5
//tyu qwe 3 3 4 5
//ji dfgvhb 4 -4 4
//iop jkl 3 2 5 4
//vbnm hjk 2 2 2 2
//ghj jkm 5 -5 5