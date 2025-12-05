package oop.interfaces

interface BookPrinter<T> {
    fun printBook(book: T) : String
}