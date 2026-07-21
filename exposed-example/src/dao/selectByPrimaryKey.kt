package dao

import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun main() {
    dbConnect()
    transaction {
        val entity = MemberEntity.findById(2)
        entity?.let(::MemberModel).let(::println)
    }
}