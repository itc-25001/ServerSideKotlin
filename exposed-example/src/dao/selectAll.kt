package dao

import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun main() {
    dbConnect()
    transaction {
        val list = MemberEntity.all().map(::MemberModel)
        list.forEach(::println)
    }
}