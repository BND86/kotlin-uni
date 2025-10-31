package delegation

/**
 * Базовая реализация ПИФа
 */
class BasicMutualFund(
    override val managementCompany: String,
    override val fundName: String,
    override val foundationYear: Int
) : MutualFund {
    override fun getInfo(): String {
        return "ПИФ: $fundName\n" +
               "Управляющая компания: $managementCompany\n" +
               "Год основания: $foundationYear"
    }
}
