package io.primeascpect.kotlindemo.toggle

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Toggle(@Value("\${toggle}") val state: Boolean) {

    fun isEnabled() = state
}