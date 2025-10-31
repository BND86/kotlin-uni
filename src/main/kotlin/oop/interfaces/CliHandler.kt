package oop.interfaces

import oop.entity.EBook

interface CliHandler {
    fun input(): Boolean

    fun output(items: List<EBook>)
}