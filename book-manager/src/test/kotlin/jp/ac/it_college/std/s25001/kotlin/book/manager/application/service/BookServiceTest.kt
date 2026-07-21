package jp.ac.it_college.std.s25001.kotlin.book.manager.application.service

import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository.BookRepository
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class BookServiceTest {
    private val expected = run {
        val releaseDate = LocalDate(1950, 10, 1)
        val book = Book(1, "Kotlin入門", "コトリン太郎", releaseDate)
        val bookWithRental = BookWithRental(book, null)
        listOf(bookWithRental)
    }

    // モック本体
    private val bookRepository = mock<BookRepository> {
        on { findAllWithRental() } doReturn expected
    }

    // サービス本体(テスト対象)
    private val bookService = BookService(bookRepository)

    @Test
    fun `getList when book list is exist than return list`() {
        val result = bookService.getList()
        assertIterableEquals(expected, result)
    }
}