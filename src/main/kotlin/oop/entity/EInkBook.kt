package oop.entity

class EInkBook(
    title: String,
    screenSize: Double,
    private val generation: EInkGeneration
) : EBook(title, screenSize) {

    enum class EInkGeneration { Pearl, Vizplex }

    override fun getSpecificDisplayInfo(): String {
        return generation.name
    }
}