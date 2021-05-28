package io.primeascpect.kotlindemo.extensions

import java.time.LocalDate

operator fun ClosedRange<LocalDate>.iterator() = object : Iterator<LocalDate> {
    private var next = this@iterator.start
    override fun hasNext(): Boolean = !next.isAfter(this@iterator.endInclusive)

    override fun next() = next.also { next = next.plusDays(1) }
}

fun ClosedRange<LocalDate>.asSequence() = iterator().asSequence()

fun ClosedRange<LocalDate>.toList() = asSequence().toList()

fun main() {
    val startDate = LocalDate.of(2020, 10, 1)
    val endDate = LocalDate.of(2020, 10, 10)
    for(date in startDate..endDate) {
        println(date)
    }
    val dates = (startDate..endDate).toList()
}