package jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository

import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.BookWithRental

interface BookRepository{
    fun findAllWithRental() : List<BookWithRental>

    fun findWithRental(bookId: Long): BookWithRental?

    fun register(book: Book)

    fun update(book: Book)

    fun delete(bookId: Long)
}