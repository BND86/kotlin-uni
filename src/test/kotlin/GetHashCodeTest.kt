import kotlin.test.assertEquals
import kotlin.test.Test

class GetHashCodeTest {

    @Test
    fun testInt() {
        assertEquals(42, getHashCode(42))
        assertEquals(0, getHashCode(0))
        assertEquals(-10, getHashCode(-10))
        assertEquals(Int.MAX_VALUE, getHashCode(Int.MAX_VALUE))
    }

    @Test
    fun testString() {
        // "ab" -> 'a'.code = 97, 'b'.code = 98 -> 97 + 98 = 195
        assertEquals(195, getHashCode("ab"))
        assertEquals(0, getHashCode(""))
        assertEquals(97, getHashCode("a"))
    }

    @Test
    fun testBoolean() {
        assertEquals(1, getHashCode(true))
        assertEquals(0, getHashCode(false))
    }

    @Test
    fun testList() {
        assertEquals(42, getHashCode(listOf(42)))
        assertEquals(195, getHashCode(listOf("ab")))
        assertEquals(1, getHashCode(listOf(true)))
        assertEquals(238, getHashCode(listOf(42, "ab", true))) // 42 + 195 + 1 = 238
        assertEquals(0, getHashCode(emptyList<Any>()))
    }

    @Test
    fun testNull() {
        assertEquals(0, getHashCode(null))
    }

    @Test
    fun testOtherTypes() {
        assertEquals(0, getHashCode(3.14))
        assertEquals(0, getHashCode(123L))
        assertEquals(0, getHashCode(mapOf("key" to "value")))
        assertEquals(0, getHashCode(arrayOf(1, 2, 3)))
    }

    @Test
    fun testNestedList() {
        // [[1, 2], "ab"] -> [1+2=3] + 195 = 198
        assertEquals(198, getHashCode(listOf(listOf(1, 2), "ab")))
    }
}