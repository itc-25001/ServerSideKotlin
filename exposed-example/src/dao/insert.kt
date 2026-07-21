package dao

import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun main() {
    dbConnect()
    transaction {
        val entity = MemberEntity.new {
            name = "Shiro"
        }
        println(MemberModel(entity))
    }
}