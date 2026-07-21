package jp.ac.it_college.std.s25001.kotlin.book.manager.presentation.controller

import jp.ac.it_college.std.s25001.kotlin.book.manager.application.service.BookService
import jp.ac.it_college.std.s25001.kotlin.book.manager.presentation.form.BookInfo
import jp.ac.it_college.std.s25001.kotlin.book.manager.presentation.form.GetBookDetailResponse
import jp.ac.it_college.std.s25001.kotlin.book.manager.presentation.form.GetBookListResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/book")
@CrossOrigin
class BookController(
    private val bookService: BookService
) {
    @GetMapping("/list")
    fun getList(): GetBookListResponse {
        val bookList = bookService.getList().map(::BookInfo)
        return GetBookListResponse(bookList)
    }

    @GetMapping("/detail/{bookId}")
    fun getDetail(@PathVariable bookId: Long): GetBookDetailResponse {
        val book = bookService.getDetail(bookId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")
        return GetBookDetailResponse(book)
    }
}