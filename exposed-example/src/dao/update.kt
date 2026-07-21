package dao

import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun main() {
    dbConnect()
    transaction {
        val entity = MemberEntity.findById(4)
        println(entity?.let(::MemberModel))
        entity?.apply {
            name = "Yonro"
        }
        println(entity?.let(::MemberModel))
    }
}