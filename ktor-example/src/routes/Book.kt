package jp.ac.it_college.std.s25001.kotlin.ktor.example.routes

import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import kotlinx.serialization.Serializable

@Resource("/book")
class Book {
    @Resource("/detail")
    class Detail(val parent: Book = Book()) {
        @Resource("/{id}")
        class Id(val parent: Detail = Detail(), val id: Long)
    }

    @Resource("/register")
    class Register(val parent: Book = Book())
}

@Serializable
data class BookResponse(
    val id: Long,
    val title: String,
    val author: String,
)

@Serializable
data class RegisterRequest(
    val id: Long,
    val title: String,
    val author: String,
)

fun Routing.bookRoutes() {
    get<Book.Detail.Id> { param ->
        val response = BookResponse(param.id, "Kotlin入門", "コトリン太郎")
        call.respond(response)
    }

    post<Book.Register> { param ->
        val request = call.receive<RegisterRequest>()

        // 実際は登録処理とかやる

        call.respondText("registered. id=${request.id} title=${request.title} author=${request.author}")
    }
}