package jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

class BookWithRentalTest {
    @Test
    fun `isRental when rental is null then return false`() {
        val releaseDate = LocalDate(1950, 10, 1)
        val book = Book(1, "Kotlin入門", "コトリン太郎", releaseDate)
        val bookWithRental = BookWithRental(book, null)

        assertFalse(bookWithRental.isRental)
    }

    @Test
    fun `isRental when rental is not null then return true`() {
        val releaseDate = LocalDate(1950, 10, 1)
        val book = Book(1, "Kotlin入門", "コトリン太郎", releaseDate)

        val now = Clock.System.now()
        val rentalDatetime = now.toLocalDateTime(TimeZone.of("Asia/Tokyo"))
        val returnDeadline = now.plus(14.days).toLocalDateTime(TimeZone.of("Asia/Tokyo"))
        val rental = Rental(1, 100, rentalDatetime, returnDeadline)

        val bookWithRental = BookWithRental(book, rental)

        assertTrue(bookWithRental.isRental)
    }
}