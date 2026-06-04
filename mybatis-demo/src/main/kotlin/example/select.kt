package example

import database.UserDynamicSqlSupport
import database.UserMapper
import database.selectByPrimaryKey
import database.select
import org.mybatis.dynamic.sql.util.kotlin.elements.isEqualTo
import database.UserDynamicSqlSupport.name
import database.UserDynamicSqlSupport.age
import database.count

fun main(){
    createSessionFactory().openSession().use{
        session ->
        val mapper = session.getMapper(UserMapper::class.java)

        println("----主キー検索")
        val user = mapper.selectByPrimaryKey(100)
        println(user)

        println("--Where 句での検索")
        val userList1 = mapper.select{
            where{
                name isEqualTo "Jiro"
            }
        }
        println(userList1)

        println("-- Where 句での検索で複数帰ってくるパターン")
        val userList2 = mapper.select{
            where{
                age isGreaterThanOrEqualTo 25
            }
        }
        println(userList2)

        println("---- count の使用")
        // SELECT COUNT(*) FROM user WHERE age >= 25,
        val count1 = mapper.count{
            where {
                age isGreaterThanOrEqualTo 25
            }
        }
        println("age >= 25 のデータ件数: $count1")

        println("--- データ全件数える")
        val count2 = mapper.count{
            allRows()
        }
        println("データ全件数: $count2")
    }
}