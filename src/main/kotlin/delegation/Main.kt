package delegation

import java.time.LocalDate

fun main() {
    println("=".repeat(80))
    println("Система учёта паевых инвестиционных фондов")
    println("=".repeat(80))
    println()

    val funds = createSampleFunds()
    
    println("Все ПИФы в системе:")
    println("-".repeat(80))
    funds.forEachIndexed { index, fund ->
        println("${index + 1}.")
        println(fund.getInfo())
        println("-".repeat(80))
    }

    println("\nПИФы, основанные до 2008 года:")
    println("=".repeat(80))
    val fundsBefore2008 = funds.filter { it.foundationYear < 2008 }
    
    if (fundsBefore2008.isEmpty()) {
        println("Нет ПИФов, основанных до 2008 года")
    } else {
        fundsBefore2008.forEachIndexed { index, fund ->
            println("${index + 1}.")
            println(fund.getInfo())
            println("-".repeat(80))
        }
    }

    println("\nСтатистика:")
    println("Всего ПИФов: ${funds.size}")
    println("ПИФов до 2008 года: ${fundsBefore2008.size}")
}

fun createSampleFunds(): List<MutualFund> {
    return listOf(
        // Обычный ПИФ, основанный до 2008
        BasicMutualFund(
            managementCompany = "Альфа-Капитал",
            fundName = "Альфа-Капитал Баланс",
            foundationYear = 2005
        ),
        
        // Интервальный ПИФ, основанный до 2008
        IntervalMutualFund(
            base = BasicMutualFund(
                managementCompany = "ВТБ Капитал Управление активами",
                fundName = "ВТБ - Фонд Облигаций",
                foundationYear = 2006
            ),
            intervalStart = LocalDate.of(2025, 11, 1),
            intervalEnd = LocalDate.of(2025, 11, 30)
        ),
        
        // ПИФ акций первого эшелона, основанный до 2008
        StockMutualFund(
            base = BasicMutualFund(
                managementCompany = "Сбербанк Управление Активами",
                fundName = "Сбербанк - Голубые фишки",
                foundationYear = 2007
            ),
            stockTier = StockTier.FIRST
        ),
        
        // ПИФ акций второго эшелона, основанный после 2008
        StockMutualFund(
            base = BasicMutualFund(
                managementCompany = "УК Открытие",
                fundName = "Открытие - Акции второго эшелона",
                foundationYear = 2010
            ),
            stockTier = StockTier.SECOND
        ),
        
        // Обычный ПИФ, основанный после 2008
        BasicMutualFund(
            managementCompany = "Райффайзен Капитал",
            fundName = "Райффайзен - Еврооблигации",
            foundationYear = 2012
        ),
        
        // Интервальный ПИФ, основанный после 2008
        IntervalMutualFund(
            base = BasicMutualFund(
                managementCompany = "Газпромбанк - Управление активами",
                fundName = "Газпромбанк - Сбалансированный",
                foundationYear = 2015
            ),
            intervalStart = LocalDate.of(2025, 11, 15),
            intervalEnd = LocalDate.of(2025, 12, 15)
        ),
    )
}
