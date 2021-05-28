package io.primeascpect.kotlindemo

import arrow.core.Try
import arrow.core.orNull

data class Result(val os: String?, val city: String?, val hasErrors: Boolean, val osError: String?, val cityError: String? /* много полей */)

/**
 * Input format: "ip;userAgent". Example: 10.4.5.6;Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.3
 */
fun parse(input: String): Result {
    val parts = input.split(";")
    val city = Try { parts[0] }.flatMap { Try { getCityByIP(it) } }
    val os = Try { parts[1] }.flatMap { Try { getOsByUserAgent(it) } }
    return Result(
        os = os.orNull(),
        city = city.orNull(),
        hasErrors = os.isFailure() || city.isFailure(),
        osError = os.errorMessage(),
        cityError = city.errorMessage()
    )
}

fun getOsByUserAgent(ua: String): String = TODO()

fun getCityByIP(ip: String): String = TODO()

fun <T> Try<T>.errorMessage() = failed().map { it.message }.orNull()

fun main() {
    println(parse("1.2.3"))
}