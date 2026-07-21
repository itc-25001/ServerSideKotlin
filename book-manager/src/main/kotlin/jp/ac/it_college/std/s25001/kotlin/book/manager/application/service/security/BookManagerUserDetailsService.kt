package jp.ac.it_college.std.s25001.kotlin.book.manager.application.service.security

import jp.ac.it_college.std.s25001.kotlin.book.manager.application.service.AuthenticationService
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.model.User
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.type.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class BookManagerUserDetailsService(
    private val authenticationService: AuthenticationService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = authenticationService.findUser(username)
            ?: throw UsernameNotFoundException("$username に該当するユーザはいません")
        return BookManagerUserDetails(user)
    }
}

data class BookManagerUserDetails(
    val id: Long,
    val email: String,
    private val password: String,
    val roleType: RoleType
) : UserDetails {
    constructor(user: User) : this(
        id = user.id,
        email = user.email,
        password = user.password,
        roleType = user.roleType
    )

    override fun getAuthorities(): Collection<out GrantedAuthority> {
        return AuthorityUtils.createAuthorityList(
            roleType.name
        )
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String {
        return email
    }

}