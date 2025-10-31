package oop.interfaces

import oop.entity.EBook

interface Repository {
    val books: MutableList<EBook>

    fun addBook(book: EBook)

    fun getAllBooks(): List<EBook>
}