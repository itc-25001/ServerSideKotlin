package jp.ac.it_college.std.s25001.kotlin.book.manager.presentation.controller

import jp.ac.it_college.std.s25001.kotlin.book.manager.application.service.BookService
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.Book
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.BookWithRental
import jp.ac.it_college.std.s25001.kotlin.book.manager.presentation.form.BookInfo
import jp.ac.it_college.std.s25001.kotlin.book.manager.presentation.form.GetBookListResponse
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class BookControllerTest {
    // 前提となるデータ
    private val bookId = 100L

    // モックの作成
    private val bookService = mock<BookService> {
        on { getList() } doReturn listOf(
            BookWithRental(
                Book(bookId, "Kotlin入門", "コトリン太郎", LocalDate(1950, 10, 1)),
                null
            )
        )
    }

    // テスト対象のコントローラを生成
    private val bookController = BookController(bookService)

    @Test
    fun `getList is success`() {
        // 期待するレスポンスデータの準備
        val expectedResponse = GetBookListResponse(
            listOf(BookInfo(bookId, "Kotlin入門", "コトリン太郎", false))
        )
        val expected = Json.encodeToString(expectedResponse)

        // MockMvc(クライアントのモック)を用意
        val mockMvc = MockMvcBuilders.standaloneSetup(bookController).build()

        mockMvc.get("/book/list") {
            // リクエストボディがある場合に設定する
            // (例↓)
            // contentType = MediaType.APPLICATION_JSON
            // content = "JSON文字列データを指定する箇所"
        }.andExpect {
            status {
                isOk()
            }
            content {
                contentType(MediaType.APPLICATION_JSON)
                json(expected)
            }
        }
    }
}