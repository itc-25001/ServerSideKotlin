package jp.ac.it_college.std.s25001.kotlin.ktor.example

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

fun Application.configureContentNegotiation() {
    install(ContentNegotiation) {
        json(Json {
            // 出力時に人が読みやすい形(改行・インデント込)で出力するか
            prettyPrint = true
            // JSON→オブジェクト変換時に不明なキーを無視するか
            ignoreUnknownKeys = true
            // JSONのキー名とオブジェクトのプロパティ名の命名規則変換をどうするか
            @OptIn(ExperimentalSerializationApi::class)
            namingStrategy = JsonNamingStrategy.SnakeCase
        })
    }
}