package io.primeascpect.kotlindemo.reactive

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "integration")
@ConstructorBinding
data class ConstructorBindingExample(
    val url: String,
    val user: String,
    val password: String)