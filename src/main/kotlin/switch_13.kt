fun main() {
    print("Введите дату в формате ГГГГ-ММ-ДД: ")
    val input = readlnOrNull() ?: ""

    processDateInput(input)?.let { (futureYear, futureMonth, futureDay, isLastDay) ->
        println("Дата через 2 месяца: ${formatDate(futureYear, futureMonth, futureDay)}")
        println("Является ли последним днем месяца: $isLastDay")
    } ?: println("Ошибка: введите дату в корректном формате (ГГГГ-ММ-ДД)")
}

data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)

fun processDateInput(input: String) =
    input.split("-").takeIf { it.size == 3 }?.let { parts ->

        val year = parts[0].toIntOrNull()
        val month = parts[1].toIntOrNull()
        val day = parts[2].toIntOrNull()

        if (year != null && month != null && day != null && isValidDate(year, month, day)) {

            val (futureYear, futureMonth, futureDay) = calculateFutureDate(year, month, day)
            Quadruple(futureYear, futureMonth,
                futureDay, futureDay == getDaysInMonth(futureMonth, futureYear))
        }
        else {
            null
        }
    }

fun calculateFutureDate(year: Int, month: Int, day: Int): Triple<Int, Int, Int> {
    var futureMonth = month + 2
    var futureYear = year

    if (futureMonth > 12) {
        futureMonth -= 12
        futureYear++
    }

    val daysInFutureMonth = getDaysInMonth(futureMonth, futureYear)
    val futureDay = minOf(day, daysInFutureMonth)

    return Triple(futureYear, futureMonth, futureDay)
}

fun getDaysInMonth(month: Int, year: Int) = when (month) {
    2 -> if (isLeapYear(year)) 29 else 28
    4, 6, 9, 11 -> 30
    else -> 31
}

fun isLeapYear(year: Int) = when {
    year % 400 == 0 -> true
    year % 100 == 0 -> false
    else -> year % 4 == 0
}

fun isValidDate(year: Int, month: Int, day: Int) =
    month in 1..12 && day in 1..31 && day <= getDaysInMonth(month, year)

fun formatDate(year: Int, month: Int, day: Int) =
    "${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
