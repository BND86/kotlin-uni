package oop

import oop.entity.EBook
import oop.interfaces.Repository

object EBookRepository : Repository {
    override val books = mutableListOf<EBook>()

    override fun addBook(book: EBook) {
        books.add(book)
    }

    override fun getAllBooks(): List<EBook> = books.toList()
}