package jp.ac.it_college.std.s25001.kotlin.book.manager.domain.repository

import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.User

interface UserRepository{
    fun find(email:String): User?

    fun find(id: Long): User?
}