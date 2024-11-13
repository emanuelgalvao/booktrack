package com.emanuelgalvao.booktrack.util.extensions

import com.emanuelgalvao.booktrack.data.database.model.ReadingBook
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class ReadingBookExtensionTest {

    @Test
    fun `readProgress method should return correctly to different values`() {
        val mockedReadingBook1: ReadingBook = mockk(relaxed = true) {
            every { totalPages } returns "100"
            every { currentPage } returns 0
        }
        val mockedReadingBook2: ReadingBook = mockk(relaxed = true) {
            every { totalPages } returns "50"
            every { currentPage } returns 25
        }
        val mockedReadingBook3: ReadingBook = mockk(relaxed = true) {
            every { totalPages } returns "50"
            every { currentPage } returns 75
        }

        assertEquals(0f, mockedReadingBook1.readProgress())
        assertEquals(0.5f, mockedReadingBook2.readProgress())
        assertEquals(1.5f, mockedReadingBook3.readProgress())
    }

    @Test
    fun `toCurrentReadData method should convert to CurrentReadData correctly`() {
        val readingBook = ReadingBook(
            id = "id",
            imageUrl = "imageUrl",
            title = "Título",
            subtitle = "Subtítulo",
            author = "Autor",
            totalPages = "200",
            description = "Descrição",
            isReading = true,
            currentPage = 50
        )

        val currentReadData = readingBook.toCurrentReadData()

        assertEquals("id", currentReadData.id)
        assertEquals("imageUrl", currentReadData.imageUrl)
        assertEquals("Título", currentReadData.title)
        assertEquals("Autor", currentReadData.author)
        assertEquals("50", currentReadData.currentPage)
        assertEquals("200", currentReadData.totalPages)
        assertEquals(0.25f, currentReadData.readProgress)
    }

    @Test
    fun `toBookListData method should convert to BookListData correctly`() {
        val readingBook = ReadingBook(
            id = "id",
            imageUrl = "imageUrl",
            title = "Título",
            subtitle = "Subtítulo",
            author = "Autor",
            totalPages = "200",
            description = "Descrição",
            isReading = true,
            currentPage = 50
        )

        val bookListData = readingBook.toBookListData()

        assertEquals("id", bookListData.id)
        assertEquals("imageUrl", bookListData.imageUrl)
    }

    @Test
    fun `toBookDetailsCardData method should convert to BookDetailsCardData correctly`() {
        val readingBook = ReadingBook(
            id = "id",
            imageUrl = "imageUrl",
            title = "Título",
            subtitle = "Subtítulo",
            author = "Autor",
            totalPages = "200",
            description = "Descrição",
            isReading = true,
            currentPage = 50
        )

        val bookDetailsCardData = readingBook.toBookDetailsCardData()

        assertEquals("id", bookDetailsCardData.id)
        assertEquals("imageUrl", bookDetailsCardData.imageUrl)
        assertEquals("Título", bookDetailsCardData.title)
        assertEquals("Subtítulo", bookDetailsCardData.subtitle)
        assertEquals("Autor", bookDetailsCardData.author)
        assertEquals("200", bookDetailsCardData.totalPages)
        assertEquals("Descrição", bookDetailsCardData.description)
    }
}