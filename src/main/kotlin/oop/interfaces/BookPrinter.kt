package oop.interfaces

import oop.entity.EBook

interface BookPrinter<T : EBook> {
    fun printBook(book: T) : String
}