package delegation

/**
 * Декоратор для ПИФа акций
 * Использует делегирование через 'by' для расширения функциональности базового ПИФа
 */
class StockMutualFund(
    private val base: MutualFund,
    val stockTier: StockTier
) : MutualFund by base {
    override fun getInfo(): String {
        return base.getInfo() + "\n" +
               "Тип: ПИФ акций\n" +
               "Эшелон акций: $stockTier"
    }
}
