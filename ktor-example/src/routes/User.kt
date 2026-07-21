package jp.ac.it_college.std.s25001.kotlin.ktor.example.routes

import io.ktor.resources.Resource
import io.ktor.server.resources.get
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing


@Resource("/user")
class User {
    @Resource("/{id}")
    class Id(val parent: User = User(), val id: Long)

    @Resource("/detail")
    class Detail(val parent: User = User()) {
        @Resource("/{id}")
        class Id(val parent: Detail = Detail(), val id: Long)
    }

    @Resource("/search")
    class Search(val parent: User = User(), val q: String? = null)
}

fun Routing.userRoutes() {
    get<User.Id> { param ->
        val id = param.id
        call.respondText("id=$id")
    }

    get<User.Detail.Id> { param ->
        val id = param.id
        call.respondText("getDetail id=$id")
    }

    get<User.Search> { param ->
        val query = param.q

        // 検索文字列がない(null)場合
        if (query == null) {
            call.respondText("All Users! q=$query")
            return@get
        }
        // 検索文字列がある(not null)場合
        call.respondText("searching user... q=$query")
    }
}