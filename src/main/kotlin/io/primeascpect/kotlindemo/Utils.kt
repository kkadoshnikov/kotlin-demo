package io.primeascpect.kotlindemo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.GenericTypeResolver
import kotlin.reflect.KClass

fun Any.getLogger(): Logger = LoggerFactory.getLogger(javaClass)

fun <T> KClass<*>.resolveGenericType(typeName: String): Class<T> {
    return GenericTypeResolver.getTypeVariableMap(this.java).entries.find { it.key.name == typeName }?.value as Class<T>
}