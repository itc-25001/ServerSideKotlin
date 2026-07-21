package dsl

import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun main() {
    // 使用するデータベースへの接続
    Database.connect(
        url = "jdbc:postgresql://127.0.0.1:5432/exposed_example",
        driver = "org.postgresql.Driver",
        user = "exposed",
        password = "kotlin+exposed"
    )

    transaction {
        // ログ出力の有効化
        addLogger(StdOutSqlLogger)

        // テーブルがなければ作ってもらう
        SchemaUtils.create(Member)

        // データの挿入
        val id = Member.insert {
            it[name] = "Kotlin"
        } get Member.id
        println("Inserted id: $id")

        // 新規挿入レコードの id を使って SELECT してみる
        val member = Member.selectAll()
            .where { Member.id eq id }
            .single()
        println("id: ${member[Member.id]}")
        println("name: ${member[Member.name]}")
    }
}