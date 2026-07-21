package jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.repository

import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.User
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository.UserRepository
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.type.RoleType
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.UserDynamicSqlSupport
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.UserMapper
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.selectByPrimaryKey
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.mapper.selectOne
import org.mybatis.dynamic.sql.SqlBuilder.where
import org.mybatis.dynamic.sql.util.kotlin.GroupingCriteriaCollector.Companion.where
import org.springframework.stereotype.Repository
import jp.ac.it_college.std.s25001.kotlin.book.manager.infrastructure.database.record.User as UserRecord

@Repository
class UserRepositoryImpl(
    private val userMapper: UserMapper
) : UserRepository {
    override fun find(email: String): User? {
        val record = userMapper.selectOne {
            where {
                UserDynamicSqlSupport.email isEqualTo email
            }
        }
        return record?.let(::toModel)
    }

    override fun find(id: Long): User? {
        return userMapper.selectByPrimaryKey(id)?.let(::toModel)
    }

    private fun toModel(record: UserRecord): User {
       return record.run {
            User(
                id = id,
                email = email,
                password = password,
                name = name,
                roleType = roleType ?: RoleType.USER,
            )
        }
    }
}