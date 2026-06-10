package example

import database.User
import database.UserMapper
import database.insert

fun main(){
    val user = User(103,"Shiro",18,"Hello")
    createSessionFactory().openSession().use { session ->
        val mapper = session.getMapper(UserMapper::class.java)
        // INSERT INTO user VALUES (103,"Shiro",18,"Hello"):
        val count = mapper.insert(user)
        session.commit()
        println("${count}行のレコードを挿入しました。")
    }
}