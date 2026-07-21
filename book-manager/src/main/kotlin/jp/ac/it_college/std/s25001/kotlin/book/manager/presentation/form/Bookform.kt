package jp.ac.it_college.std.s25001.kotlin.book.manager.presentation.form

import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.Rental
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetBookListResponse(
    val bookList: List<BookInfo>
)

@Serializable
data class BookInfo(
    val id: Long,
    val title: String,
    val author: String,
    val isRental: Boolean,
) {
    constructor(model: BookWithRental) : this(
        id = model.book.id,
        title = model.book.title,
        author = model.book.author,
        isRental = model.isRental
    )
}

@Serializable
data class GetBookDetailResponse(
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate,
    val rentalInfo: RentalInfo?
) {
    constructor(model: BookWithRental) : this(
        id = model.book.id,
        title = model.book.title,
        author = model.book.author,
        releaseDate = model.book.releaseDate,
        rentalInfo = model.rental?.let(::RentalInfo)
    )
}

@Serializable
data class RentalInfo(
    val userId: Long,
    val rentalDatetime: LocalDateTime,
    val returnDeadline: LocalDateTime,
) {
    constructor(model: Rental) : this(
        userId = model.userId,
        rentalDatetime = model.rentalDateTime,
        returnDeadline = model.returnDeadline,
    )
}

@Serializable
data class RegisterBookRequest(
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate
)
@Serializable
data class UpdateBookRequest(
    val id: Long,
    val title: String?,
    val author: String?,
    val releaseDate: LocalDate?
)