package jp.ac.it_college.std.s25001.kotlin.book.manager.application.service

import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.Rental
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository.RentalRepository
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository.UserRepository
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

// 貸出期間
private const val RENTAL_TERM_DAYS = 14L
private val JST = TimeZone.of("Asia/Tokyo")

@Service
class RentalService(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val rentalRepository: RentalRepository
) {
    @Transactional
    fun startRental(bookId: Long, userId: Long) {
        // ユーザー情報をチェック
        userRepository.find(userId)
            ?: throw IllegalArgumentException("該当するユーザーが存在しません userId: $userId")
        // 書籍の存在チェック
        val book = bookRepository.findWithRental(bookId)
            ?: throw IllegalArgumentException("該当する書籍が存在しません bookId: $bookId")
        // 貸出中のチェック
        if (book.isRental) throw IllegalArgumentException("貸出中の書籍です bookId: $bookId")

        // 現在日時の取得
        val now = Clock.System.now()
        val rentalDatetime = now.toLocalDateTime(JST)
        val returnDeadline =
            now.plus(RENTAL_TERM_DAYS.days).toLocalDateTime(JST)

        rentalRepository.startRental(
            Rental(bookId, userId, rentalDatetime, returnDeadline)
        )
    }

    @Transactional
    fun endRental(bookId: Long, userId: Long) {
        // ユーザー情報をチェック
        userRepository.find(userId)
            ?: throw IllegalArgumentException("該当するユーザーが存在しません userId: $userId")
        // 書籍の存在チェック
        val book = bookRepository.findWithRental(bookId)
            ?: throw IllegalArgumentException("該当する書籍が存在しません bookId: $bookId")
        // 貸出中かどうかチェック
        if (!book.isRental) throw IllegalStateException("貸出中ではない書籍です ID: $bookId")
        // 借りたユーザーが返そうとしているかチェック
        book.rental?.let { rental ->
            if (rental.userId != userId) throw IllegalStateException("他のユーザーが貸出中の書籍です userId: $userId bookId: $bookId")
        }

        rentalRepository.endRental(bookId)
    }
}