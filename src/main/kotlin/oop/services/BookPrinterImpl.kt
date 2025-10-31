package oop.services

import oop.entity.EBook
import oop.entity.EInkBook
import oop.interfaces.BookPrinter

class BookPrinterImpl<T: EBook> () : BookPrinter<T> {
    override fun printBook(book: T): String {
        return when (book) {
            is EInkBook -> "\"${book.getTitle()}\" - ${book.getScreenSize()} - E-Ink ${book.getSpecificDisplayInfo()}"
            else -> "\"${book.getTitle()}\" - ${book.getScreenSize()} - LCD (${book.getSpecificDisplayInfo()} colors)"
        }
    }
}