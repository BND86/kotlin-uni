fun getHashCode(value: Any?): Int {
    return when (value) {
        is Int -> value
        is String -> value.sumOf { it.code }
        is Boolean -> if (value) 1 else 0
        is List<*> -> value.sumOf { getHashCode(it) }
        else -> 0
    }
}