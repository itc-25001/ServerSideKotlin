package example

import database.User
import database.UserDynamicSqlSupport.name
import database.UserDynamicSqlSupport.profile
import database.UserMapper
import database.insertMultiple
import database.update

fun main() {
    val userList = listOf(
        User(104, "Goro", 15, "hello"),
        User(105, "Rokuro", 14, "Hello")
    )

    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)

        val count = mapper.insertMultiple(userList)
        session.commit()
        println("${count}行のレコードを追加しました")

        println("---- 主キー以外を条件にして更新(データクラスは使えません)")

        val count2 = mapper.update {
            set(profile) equalTo "Good Morning"
            where {
                name isEqualTo "Shiro"
            }
        }

        session.commit()
        println("${count2}行のレコードを更新しました")
    }
}