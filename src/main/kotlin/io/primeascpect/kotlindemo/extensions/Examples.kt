package io.primeascpect.kotlindemo.extensions

fun main() {
    val list = listOf(1, 2, 3, 5, 7, 9)

    println("first: " + list.first { it % 3 == 0 })

    println("foldRight: " + list.foldRight("") { i, acc -> acc + i })

    println("reversed: " + list.asReversed())

    println("associate: " + list.associate { "$it^2" to it * it })

    println("zip: " + list.zip(listOf("a", "b", "c", "d", "e", "f")))

    println("1..5: " + (1..5).toList())

    println("mapOf: " + mapOf("a" to 1, "b" to 5))

    println("indexed: " + list.mapIndexed{ index, i -> "list[$index]=$i" })

    list.elementAtOrNull(15) ?: return

    println("this code is unreachable")
}