package io.primeascpect.kotlindemo.extensions

import org.springframework.stereotype.Component

@Component
class SpringExecutor(executors: List<Executor>) {
    private val executors: Map<ExecutorType, Executor> = executors.associateBy(Executor::type)

    fun execute(command: () -> Unit, type: ExecutorType) = executors[type]?.execute(command)
}

interface Executor {
    fun type(): ExecutorType

    fun execute(command: () -> Unit)
}

@Component
class SyncExecutor: Executor {
    override fun type() = ExecutorType.SYNC
    override fun execute(command: () -> Unit) = TODO()
}

@Component
class AsyncExecutor: Executor {
    override fun type() = ExecutorType.ASYNC
    override fun execute(command: () -> Unit) = TODO()
}

enum class ExecutorType {
    SYNC, ASYNC
}

