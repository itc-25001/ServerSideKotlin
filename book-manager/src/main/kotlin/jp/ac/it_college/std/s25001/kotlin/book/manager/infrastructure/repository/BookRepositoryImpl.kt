package jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.repository

import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.Rental
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository.BookRepository
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.BookMapper
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.BookWithRentalDynamicSqlSupport.id
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.BookWithRentalMapper
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.deleteByPrimaryKey
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.insert
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.select
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.selectOne
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.updateByPrimaryKey
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.springframework.stereotype.Repository
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.record.Book as BookRecord
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.record.BookWithRental as BookWithRentalRecord

@Repository
class BookRepositoryImpl(
    private val bookWithRentalMapper: BookWithRentalMapper,
    private val bookMapper: BookMapper
) : BookRepository {

    override fun findAllWithRental(): List<BookWithRental> {
        return bookWithRentalMapper.select { }.map(::toModel)
    }

    override fun findWithRental(bookId: Long): BookWithRental? {
        return bookWithRentalMapper.selectOne {
            where {
                id isEqualTo bookId
            }
        }?.let(::toModel)
    }

    override fun register(book: Book) {
        bookMapper.insert(toRecord(book))
    }

    override fun update(book: Book) {
        bookMapper.updateByPrimaryKey(toRecord(book))
    }

    override fun delete(bookId: Long) {
        bookMapper.deleteByPrimaryKey(bookId)
    }

    private fun toModel(record: BookWithRentalRecord): BookWithRental {
        val book = record.run {
            Book(
                id = id,
                title = title,
                author = author,
                releaseDate = releaseDate.toKotlinLocalDate()
            )
        }
        val rental = with(record) {
            if (userId != null && rentalDatetime != null && returnDeadline != null) {
                Rental(
                    bookId = id,
                    userId = userId,
                    rentalDateTime = rentalDatetime.toKotlinLocalDateTime(),
                    returnDeadline = returnDeadline.toKotlinLocalDateTime()
                )
            } else {
                null
            }
        }

        return BookWithRental(book, rental)
    }

    private fun toRecord(model: Book): BookRecord {
        return model.run {
            BookRecord(id, title, author, releaseDate.toJavaLocalDate())
            }
        }
}