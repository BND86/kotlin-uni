package delegation

/**
 * Перечисление для эшелонов акций ПИФов
 */
enum class StockTier {
    FIRST {
        override fun toString() = "Первый эшелон"
    },
    SECOND {
        override fun toString() = "Второй эшелон"
    },
    OTHER {
        override fun toString() = "Другие эшелоны"
    }
}
