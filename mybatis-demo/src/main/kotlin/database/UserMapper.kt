/*
 * Auto-generated file. Created by MyBatis Generator
 */
package database

import database.User
import database.UserDynamicSqlSupport.age
import database.UserDynamicSqlSupport.id
import database.UserDynamicSqlSupport.name
import database.UserDynamicSqlSupport.profile
import database.UserDynamicSqlSupport.user
import org.apache.ibatis.annotations.Arg
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.ResultMap
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.type.JdbcType
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider
import org.mybatis.dynamic.sql.util.SqlProviderAdapter
import org.mybatis.dynamic.sql.util.kotlin.CountCompleter
import org.mybatis.dynamic.sql.util.kotlin.DeleteCompleter
import org.mybatis.dynamic.sql.util.kotlin.SelectCompleter
import org.mybatis.dynamic.sql.util.kotlin.UpdateCompleter
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.countFrom
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.deleteFrom
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.insert
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.insertMultiple
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectDistinct
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectList
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.selectOne
import org.mybatis.dynamic.sql.util.kotlin.mybatis3.update
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonInsertMapper
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper

@Mapper
interface UserMapper : CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<User>, CommonUpdateMapper {
    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @Results(id="UserResult")
    @Arg(column="id", jdbcType=JdbcType.INTEGER, javaType=Int::class, id=true)
    @Arg(column="name", jdbcType=JdbcType.VARCHAR, javaType=String::class)
    @Arg(column="age", jdbcType=JdbcType.INTEGER, javaType=Int::class)
    @Arg(column="profile", jdbcType=JdbcType.VARCHAR, javaType=String::class)
    fun selectMany(selectStatement: SelectStatementProvider): List<User>

    @SelectProvider(type=SqlProviderAdapter::class, method="select")
    @ResultMap("UserResult")
    fun selectOne(selectStatement: SelectStatementProvider): User?
}

fun UserMapper.count(completer: CountCompleter) =
    countFrom(this::count, user, completer)

fun UserMapper.delete(completer: DeleteCompleter) =
    deleteFrom(this::delete, user, completer)

fun UserMapper.deleteByPrimaryKey(id_: Int) =
    delete {
        where { id isEqualTo id_ }
    }

fun UserMapper.insert(row: User) =
    insert(this::insert, row, user) {
        withMappedColumn(id)
        withMappedColumn(name)
        withMappedColumn(age)
        withMappedColumn(profile)
    }

fun UserMapper.insertMultiple(records: Collection<User>) =
    insertMultiple(this::insertMultiple, records, user) {
        withMappedColumn(id)
        withMappedColumn(name)
        withMappedColumn(age)
        withMappedColumn(profile)
    }

fun UserMapper.insertMultiple(vararg records: User) =
    insertMultiple(records.toList())

fun UserMapper.insertSelective(row: User) =
    insert(this::insert, row, user) {
        withMappedColumn(id)
        withMappedColumn(name)
        withMappedColumn(age)
        withMappedColumn(profile)
    }

private val columnList = listOf(id, name, age, profile)

fun UserMapper.select(completer: SelectCompleter) =
    selectList(this::selectMany, columnList, user, completer)

fun UserMapper.selectOne(completer: SelectCompleter) =
    selectOne(this::selectOne, columnList, user, completer)

fun UserMapper.selectDistinct(completer: SelectCompleter) =
    selectDistinct(this::selectMany, columnList, user, completer)

fun UserMapper.selectByPrimaryKey(id_: Int) =
    selectOne {
        where { id isEqualTo id_ }
    }

fun UserMapper.update(completer: UpdateCompleter) =
    update(this::update, user, completer)

fun UserMapper.updateByPrimaryKey(row: User) =
    update {
        set(name) equalTo row::name
        set(age) equalTo row::age
        set(profile) equalTo row::profile
        where { id isEqualTo row.id }
    }