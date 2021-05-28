package io.primeascpect.kotlindemo.delegate

import io.primeascpect.kotlindemo.getLogger

fun <T> Collection<T>.withSpy() : Collection<T> = object: Collection<T> by this {
    val log = getLogger()

    override fun isEmpty(): Boolean {
        log.info("Call is empty")
        return this@withSpy.isEmpty()
    }
}

fun main() {
    val list = listOf(1, 2, 3).withSpy()
    println(list.isEmpty())
}