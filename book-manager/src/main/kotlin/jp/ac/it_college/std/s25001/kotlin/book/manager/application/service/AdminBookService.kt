package jp.ac.it_college.std.s25001.kotlin.book.manager.application.service

import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository.BookRepository
import kotlinx.datetime.LocalDate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminBookService(
    private val bookRepository: BookRepository,
) {
    @Transactional
    fun register(book: Book) {
        // IDの重複チェック
        bookRepository.findWithRental(book.id)?.let {
            throw IllegalArgumentException("すでに存在する書籍ID: ${book.id}")
        }
        // 問題なさそうなので登録する
        bookRepository.register(book)
    }

    @Transactional
    fun update(bookId: Long, title: String?, author: String?, releaseDate: LocalDate?) {
        // 書籍が存在しているかチェックと存在する場合は取得したデータをもとに更新する
        val bookWithRental = bookRepository.findWithRental(bookId)
            ?: throw IllegalArgumentException("存在しない書籍ID: $bookId")

        // 更新用のデータを作る。null でないものだけ変更される。
        val newBook = bookWithRental.book.let { original ->
            original.copy(
                title = title ?: original.title,
                author = author ?: original.author,
                releaseDate = releaseDate ?: original.releaseDate,
            )
        }
        bookRepository.update(newBook)
    }

    @Transactional
    fun delete(bookId: Long) {
        bookRepository.findWithRental(bookId)
            ?: throw IllegalArgumentException("存在しない書籍ID: $bookId")
        bookRepository.delete(bookId)
    }
}