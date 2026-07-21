package dsl

import org.jetbrains.exposed.v1.core.Table

object Member : Table("member") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 32)
}