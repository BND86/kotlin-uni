package oop.services

import oop.EBookRepository
import oop.interfaces.CliHandler
import oop.interfaces.EBookFilter

class EBookApplication(
    private val cliHandler: CliHandler,
    private val filter: EBookFilter
) {

    fun run() {
        println("=== Система управления электронными книгами ===")

        var continueInput = true
        while (continueInput) {
            continueInput = cliHandler.input()
        }

        val allBooks = EBookRepository.getAllBooks()
        val filteredBooks = filter.filter(allBooks, 7.0)
        cliHandler.output(filteredBooks)
    }
}