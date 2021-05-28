package io.primeascpect.kotlindemo.delegate

interface A {
    fun a1(): String
    fun a2(): String
    fun a3(): String
    fun a4(): String
}

interface B {
    fun b1(): String
    fun b2(): String
    fun b3(): String
    fun b4(): String
}

class AImpl: A {
    override fun a1(): String = TODO()
    override fun a2(): String = TODO()
    override fun a3(): String = TODO()
    override fun a4(): String = TODO()
}

class BImpl: B {
    override fun b1(): String = TODO()
    override fun b2(): String = TODO()
    override fun b3(): String = TODO()
    override fun b4(): String = TODO()
}

class C(a: A, b: B): A by a, B by b