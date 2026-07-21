package jp.ac.it_college.std.s25001.kotlin.book.manager.presentation.config

import jp.ac.it_college.std.s25001.kotlin.book.manager.application.service.AuthenticationService
import jp.ac.it_college.std.s25001.kotlin.book.manager.application.service.security.BookManagerUserDetailsService
import jp.ac.it_college.std.s25001.kotlin.book.manager.domain.type.RoleType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationService: AuthenticationService
) {
    @Bean
    @Order(1)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http {
            // この設定の適用範囲を指定(リクエストパスパターンベース)
            securityMatcher("/**")

            // 認可に関する設定
            authorizeHttpRequests {
                // 管理者権限を持ったユーザーは "/admin" に続くリクエストを許可
                authorize("/admin/**", hasAuthority(RoleType.ADMIN.name))
                // その他すべてのリクエストにはユーザー認証が必要
                authorize(anyRequest, authenticated)
            }

            // ユーザー認証機能の利用設定
            formLogin {
                // ログイン処理を受け付けるリクエストパス
                loginProcessingUrl = "/login"
                // ログインユーザー名とするリクエストパラメータ名
                usernameParameter = "email"
                // パスワードとするリクエストパラメータ名
                passwordParameter = "pass"
                // 誰でもアクセスを許可する
                permitAll = true
                // 認証成功時のレスポンスをカスタマイズ
                authenticationSuccessHandler =
                    AuthenticationSuccessHandler { _, response, _ ->
                        response.status = HttpStatus.OK.value()
                    }
                // 認証失敗時のレスポンスをカスタマイズ
                authenticationFailureHandler =
                    AuthenticationFailureHandler { _, response, _ ->
                        response.status = HttpStatus.UNAUTHORIZED.value()
                    }
            }

            logout {
                // ログアウト処理を受け付けるリクエストパス
                logoutUrl = "/logout"
                // 誰でもアクセス可能
                permitAll = true
                // ログアウト時のレスポンスをカスタマイズ
                logoutSuccessHandler =
                    LogoutSuccessHandler { _, response, _ ->
                        response.status = HttpStatus.OK.value()
                    }
            }

            csrf { disable() }

            exceptionHandling {
                // 未認証時に認証が必要なパス(URL)にアクセスがあった場合のレスポンスをカスタマイズ
                authenticationEntryPoint =
                    AuthenticationEntryPoint { _, response, _ ->
                        response.status = HttpStatus.UNAUTHORIZED.value()
                    }
                // 認証済みだが権限がなかった場合のレスポンスをカスタマイズ
                accessDeniedHandler =
                    AccessDeniedHandler { _, response, _ ->
                        response.status = HttpStatus.FORBIDDEN.value()
                    }
            }
        }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return BookManagerUserDetailsService(authenticationService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder(16, 32, 1, 19456, 2)
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedMethods = listOf(CorsConfiguration.ALL)
            allowedHeaders = listOf(CorsConfiguration.ALL)
            allowedOrigins = listOf(
                "http://localhost:8081"
            )
            allowCredentials = true
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}