package oop.utils

import oop.entity.EBook
import oop.interfaces.EBookFilter

class EBookFilterImpl: EBookFilter {
    override fun filter(books: List<EBook>, minSize: Double): List<EBook> {
        return books.filter { it.getScreenSize() >= minSize }
    }
}