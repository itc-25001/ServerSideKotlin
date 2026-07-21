package jp.ac.it_college.std.s25001.kotlin.book.manager.application.service

import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.Rental
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.User
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository.RentalRepository
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository.UserRepository
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.type.RoleType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.test.assertEquals

class RentalServiceTest {
    // 前提となるデータの定義
    private val userId = 100L
    private val bookId1 = 1000L
    private val bookId2 = 2000L

    // モックの作成
    private val userRepository = mock<UserRepository> {
        on { find(any<Long>()) } doReturn User(userId, "test@test.com", "pass", "kotlin", RoleType.USER)
    }

    private val bookRepository = mock<BookRepository> {
        on { findWithRental(bookId1) } doReturn BookWithRental(
            Book(bookId1, "Kotlin入門", "コトリン太郎", LocalDate(1950, 10, 1)),
            Rental(bookId1, userId, LocalDateTime(2026, 7, 2, 14, 12, 34), LocalDateTime(2026, 7, 16, 14, 12, 34))
        )
        on { findWithRental(bookId2) } doReturn BookWithRental(
            Book(bookId2, "Java入門", "ジャヴァ太郎", LocalDate(2005, 8, 29)),
            null
        )
    }

    private val rentalRepository = mock<RentalRepository>()

    // テスト対象のサービスを作る
    private val rentalService = RentalService(userRepository, bookRepository, rentalRepository)

    @Test
    fun `endRental when book is rental then delete to rental`() {
        rentalService.endRental(bookId1, userId)

        verify(userRepository).find(userId)
        verify(bookRepository).findWithRental(bookId1)
        verify(rentalRepository).endRental(bookId1)
    }

    @Test
    fun `endRental when book is not rental then throw exception`() {
        val exception = assertThrows<IllegalStateException> {
            rentalService.endRental(bookId2, userId)
        }

        assertEquals("貸出中ではない書籍です ID: $bookId2", exception.message)

        verify(userRepository).find(userId)
        verify(bookRepository).findWithRental(bookId2)
        verify(rentalRepository, times(0)).endRental(any())
    }
}