package jp.ac.it_college.std.s25001.kotlin.book.manager.presentation.config

import jdk.jfr.Experimental
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter

@Configuration
class KotlinxSerialization{
    @OptIn(ExperimentalSerializationApi::class)
    @Bean
    fun messageConverter(): KotlinSerializationJsonHttpMessageConverter{
        val json = Json{
            ignoreUnknownKeys = true
            explicitNulls = false
            namingStrategy = JsonNamingStrategy.SnakeCase
        }
        return KotlinSerializationJsonHttpMessageConverter(json)
    }
}