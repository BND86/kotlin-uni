package delegation

import java.time.LocalDate

/**
 * Декоратор для интервального ПИФа
 * Использует делегирование через 'by' для расширения функциональности базового ПИФа
 */
class IntervalMutualFund(
    private val base: MutualFund,
    val intervalStart: LocalDate,
    val intervalEnd: LocalDate
) : MutualFund by base {
    override fun getInfo(): String {
        return base.getInfo() + "\n" +
               "Тип: Интервальный ПИФ\n" +
               "Начало интервала: $intervalStart\n" +
               "Окончание интервала: $intervalEnd"
    }
}
