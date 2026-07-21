package dao

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun main() {
    dbConnect()
    transaction {
        val list = MemberEntity.find {
            MemberTable.name eq "Saburo"
        }.map(::MemberModel)
        println(list)
    }
}