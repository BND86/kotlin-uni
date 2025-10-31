package oop.interfaces

import oop.entity.EBook

interface EBookFilter {
    fun filter(books: List<EBook>, minSize: Double): List<EBook>
}