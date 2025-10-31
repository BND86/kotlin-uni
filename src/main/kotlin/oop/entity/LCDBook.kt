package oop.entity

class LCDBook(
    title: String,
    screenSize: Double,
    private val colorCount: Int
) : EBook(title, screenSize) {

    override fun getSpecificDisplayInfo(): String {
        return colorCount.toString()
    }
}