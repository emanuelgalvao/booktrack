package com.emanuelgalvao.booktrack.util.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultExtensionsTest {

    @Test
    fun `Int isPositive extension should return correctly to different values`() {
        assertEquals(false, 0.isPositive())
        assertEquals(false, (-1).isPositive())
        assertEquals(false, (-154).isPositive())
        assertEquals(true, 1.isPositive())
        assertEquals(true, 1050.isPositive())
    }

    @Test
    fun `Long isPositive extension should return correctly to different values`() {
        assertEquals(false, 0L.isPositive())
        assertEquals(false, (-1L).isPositive())
        assertEquals(false, (-154L).isPositive())
        assertEquals(true, 1L.isPositive())
        assertEquals(true, 1050L.isPositive())
    }

    @Test
    fun `String formatToApiRequest extension should return correctly to different values`() {
        assertEquals("Clean+Code", "Clean Code".formatToApiRequest())
        assertEquals("CleanCode", "CleanCode".formatToApiRequest())
        assertEquals("Clean-Code", "Clean-Code".formatToApiRequest())
        assertEquals("Clean+Code+Book", "Clean Code Book".formatToApiRequest())
    }

    @Test
    fun `Any isNull extension should return correctly to different values`() {
        val nullableString: String? = null
        val nullableInt: Int? = null
        val nullableFloat: Float? = null
        val nullableBoolean: Boolean? = null
        val nullableList: List<String>? = null

        val notNullableString: String? = ""
        val notNullableInt: Int? = 0
        val notNullableFloat: Float? = 1f
        val notNullableBoolean: Boolean? = true
        val notNullableList: List<String>? = listOf()

        assertEquals(true, nullableString.isNull())
        assertEquals(true, nullableInt.isNull())
        assertEquals(true, nullableFloat.isNull())
        assertEquals(true, nullableBoolean.isNull())
        assertEquals(true, nullableList.isNull())

        assertEquals(false, notNullableString.isNull())
        assertEquals(false, notNullableInt.isNull())
        assertEquals(false, notNullableFloat.isNull())
        assertEquals(false, notNullableBoolean.isNull())
        assertEquals(false, notNullableList.isNull())
    }

    @Test
    fun `Any isNotNull extension should return correctly to different values`() {
        val nullableString: String? = null
        val nullableInt: Int? = null
        val nullableFloat: Float? = null
        val nullableBoolean: Boolean? = null
        val nullableList: List<String>? = null

        val notNullableString: String? = ""
        val notNullableInt: Int? = 0
        val notNullableFloat: Float? = 1f
        val notNullableBoolean: Boolean? = true
        val notNullableList: List<String>? = listOf()

        assertEquals(false, nullableString.isNotNull())
        assertEquals(false, nullableInt.isNotNull())
        assertEquals(false, nullableFloat.isNotNull())
        assertEquals(false, nullableBoolean.isNotNull())
        assertEquals(false, nullableList.isNotNull())

        assertEquals(true, notNullableString.isNotNull())
        assertEquals(true, notNullableInt.isNotNull())
        assertEquals(true, notNullableFloat.isNotNull())
        assertEquals(true, notNullableBoolean.isNotNull())
        assertEquals(true, notNullableList.isNotNull())
    }
}