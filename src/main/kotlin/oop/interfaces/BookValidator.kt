package oop.interfaces

interface BookValidator<T> {
    fun validateData(title: String?, screenSizeStr: String?, additionalParam: String?): T
}