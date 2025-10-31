package oop.services

import oop.entity.EBook
import oop.interfaces.BookPrinter
import oop.interfaces.BookProcessor
import oop.interfaces.CliHandler

class CliHandlerImpl (
    private val bookProcessor: BookProcessor,
    private val bookPrinter: BookPrinter<EBook>
) : CliHandler {

    enum class BookType () {
        EInk,
        LCD
    }

    override fun input(): Boolean {
        println("\n=== Добавление новой электронной книги ===")
        println("Выберите тип книги:")
        println("1 - E-Ink")
        println("2 - LCD")
        println("0 - Завершить ввод")

        val choice = readlnOrNull()?.toIntOrNull()
        if (choice == null) {
            println("\nВвод прерван (End of Input)")
            return false
        }

        return when (choice) {
            in 1..2 -> bookProcessor.input(BookType.entries[choice-1].name)
            0 -> false
            else -> {
                println("Некорректный выбор!")
                true
            }
        }
    }

    override fun output(items: List<EBook>) {
        if (items.isEmpty()) {
            println("Нет книг, соответствующих критериям.")
            return
        }

        println("\n=== Книги с экраном не менее 7 дюймов ===")
        items.forEachIndexed { index, book ->
            println("${index + 1}. ${bookPrinter.printBook(book)}")
        }
    }
}