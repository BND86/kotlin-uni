package oop.services

import oop.EBookRepository
import oop.entity.EInkBook
import oop.entity.LCDBook
import oop.interfaces.BookProcessor
import oop.utils.EInkBookValidator
import oop.utils.LCDBookValidator

class BookProcessorImpl(
    private val eInkValidator: EInkBookValidator = EInkBookValidator(),
    private val lcdValidator: LCDBookValidator = LCDBookValidator()
): BookProcessor {

    private fun readInput(prompt: String): String? {
        print(prompt)
        return readlnOrNull().also { input ->
            if (input == null) {
                println("\nВвод прерван (End of Input)")
                throw IllegalArgumentException("INTERRUPTED")
            }
        }
    }

    override fun input(type: String): Boolean {
        return when (type) {
            "EInk" -> inputEInkBook()
            "LCD" -> inputLCDBook()
            else -> false
        }
    }

    private fun inputEInkBook(): Boolean {
        try {
            val title = readInput("Введите название книги: ")
            val screenSize = readInput("Введите размер экрана (дюймы): ")

            println("Выберите поколение E-Ink:")
            EInkBook.EInkGeneration.entries.forEachIndexed { index, generation ->
                println("${index + 1} - ${generation.name}")
            }
            val generationIndex = readInput("")

            val (validatedTitle, validatedScreenSize, validatedGeneration) =
                eInkValidator.validateData(title, screenSize, generationIndex)

            val book = EInkBook(validatedTitle, validatedScreenSize, validatedGeneration)
            EBookRepository.addBook(book)
            println("E-Ink книга успешно добавлена!")
            return true

        } catch (e: IllegalArgumentException) {
            if (e.message == "INTERRUPTED") {
                return false
            }
            println("Ошибка: ${e.message}")
            return true
        }
    }

    private fun inputLCDBook() : Boolean {
        try {
            val title = readInput("Введите название книги: ")
            val screenSize = readInput("Введите размер экрана (дюймы): ")
            val colorCount = readInput("Введите количество цветов: ")

            val (validatedTitle, validatedScreenSize, validatedColorCount) =
                lcdValidator.validateData(title, screenSize, colorCount)

            val book = LCDBook(validatedTitle, validatedScreenSize, validatedColorCount)
            EBookRepository.addBook(book)
            println("LCD книга успешно добавлена!")
            return true

        } catch (e: IllegalArgumentException) {
            if (e.message == "INTERRUPTED") {
                return false
            }
            println("Ошибка: ${e.message}")
            return true
        }
    }
}