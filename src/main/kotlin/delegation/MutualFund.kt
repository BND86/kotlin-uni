package delegation

/**
 * Базовый интерфейс для ПИФ
 */
interface MutualFund {
    val managementCompany: String
    val fundName: String
    val foundationYear: Int

    fun getInfo(): String
}
