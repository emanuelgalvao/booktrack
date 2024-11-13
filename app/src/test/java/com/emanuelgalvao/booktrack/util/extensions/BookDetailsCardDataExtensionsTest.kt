package com.emanuelgalvao.booktrack.util.extensions

import com.emanuelgalvao.booktrack.data.database.model.ReadingBook
import com.emanuelgalvao.booktrack.shared.components.BookDetailsCardData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class BookDetailsCardDataExtensionsTest {

    @Test
    fun `toReadingBook method should convert to ReadingBook correctly`() {
        val bookDetailsCardData = BookDetailsCardData(
            id = "id",
            imageUrl = "imageUrl",
            title = "Título",
            subtitle = "Subtítulo",
            author = "Autor",
            totalPages = "200",
            description = "Descrição"
        )

        val readingBook = bookDetailsCardData.toReadingBook()

        assertEquals(true, readingBook is ReadingBook)
        assertEquals("id", readingBook.id)
        assertEquals("imageUrl", readingBook.imageUrl)
        assertEquals("Título", readingBook.title)
        assertEquals("Subtítulo", readingBook.subtitle)
        assertEquals("Autor", readingBook.author)
        assertEquals("200", readingBook.totalPages)
        assertEquals("Descrição", readingBook.description)
        assertEquals(false, readingBook.isReading)
        assertEquals(0, readingBook.currentPage)
    }
}