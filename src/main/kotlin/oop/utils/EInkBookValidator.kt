package oop.utils

import oop.entity.EInkBook
import oop.interfaces.BookValidator

class EInkBookValidator : BookValidator<Triple<String, Double, EInkBook.EInkGeneration>> {
    override fun validateData(
        title: String?,
        screenSizeStr: String?,
        additionalParam: String?
    ): Triple<String, Double, EInkBook.EInkGeneration> {
        val validatedTitle = title?.takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("Название не может быть пустым")

        val screenSize = screenSizeStr?.toDoubleOrNull()
            ?: throw IllegalArgumentException("Некорректный размер экрана")

        if (screenSize <= 0) throw IllegalArgumentException("Размер экрана должен быть положительным")

        val generationIndex = additionalParam?.toIntOrNull()?.minus(1)
            ?: throw IllegalArgumentException("Некорректный выбор поколения")

        val generation = EInkBook.EInkGeneration.entries.getOrNull(generationIndex)
            ?: throw IllegalArgumentException("Некорректный выбор поколения")

        return Triple(validatedTitle, screenSize, generation)
    }
}