package oop.utils

import oop.interfaces.BookValidator

class LCDBookValidator : BookValidator<Triple<String, Double, Int>> {
    override fun validateData(
        title: String?,
        screenSizeStr: String?,
        additionalParam: String?
    ): Triple<String, Double, Int> {
        val validatedTitle = title?.takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("Название не может быть пустым")

        val screenSize = screenSizeStr?.toDoubleOrNull()
            ?: throw IllegalArgumentException("Некорректный размер экрана")

        if (screenSize <= 0) throw IllegalArgumentException("Размер экрана должен быть положительным")

        val colorCount = additionalParam?.toIntOrNull()
            ?: throw IllegalArgumentException("Некорректное количество цветов")

        if (colorCount <= 0) throw IllegalArgumentException("Количество цветов должно быть положительным")

        return Triple(validatedTitle, screenSize, colorCount)
    }
}