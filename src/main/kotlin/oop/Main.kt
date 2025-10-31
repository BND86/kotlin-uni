package oop

import oop.entity.EBook
import oop.services.BookPrinterImpl
import oop.services.BookProcessorImpl
import oop.services.CliHandlerImpl
import oop.services.EBookApplication
import oop.utils.EBookFilterImpl

fun main() {

    val bookPrinter = BookPrinterImpl<EBook>()
    val bookProcessor = BookProcessorImpl()
    val cliHandler = CliHandlerImpl(bookProcessor, bookPrinter)
    val filter = EBookFilterImpl()

    val app = EBookApplication(cliHandler, filter)
    app.run()
}