package oop.entity

abstract class EBook(
    private val title: String,
    private val screenSize: Double,
) {
    fun getScreenSize(): Double {
        return screenSize
    }
    fun getTitle(): String {
        return title
    }

    abstract fun getSpecificDisplayInfo(): String
}