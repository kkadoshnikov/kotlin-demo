package io.primeascpect.kotlindemo.delegate

class LazyExample {
    val value by lazy {
        println("really long call")
        Thread.sleep(1000)
        0
    }
}

fun main() {
    val example = LazyExample()
    println("before call")
    example.value
    println("after call")
}

//@Component
class BeanExample(lazyExample: LazyExample) {
    private val lazy by lazy { lazyExample.value }
}