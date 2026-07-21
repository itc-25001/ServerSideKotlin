package dao

import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
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
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(MemberTable)

        // INSERT 相当の処理。新規レコードを追加する。
        val member = MemberEntity.new {
            name = "Kotlin"
        }
        println("Inserted id: ${member.id}")

        // SELECT 相当の処理。今回は主キーを条件として取ってくる。
        MemberEntity.findById(member.id)?.let {
            println("id: ${it.id}")
            println("name: ${it.name}")
        }
    }
}