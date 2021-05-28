package io.primeascpect.kotlindemo.reactive

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

interface UserRepository {
    suspend fun save(user: User)

    suspend fun getById(id: Int): User?

    fun getAll(): Flow<User>
}

@Component
class InMemoryRepository: UserRepository {
    val users = ConcurrentHashMap<Int, User>()

    override suspend fun save(user: User) {
        users[user.id] = user
    }

    override suspend fun getById(id: Int) = users[id]

    override fun getAll(): Flow<User> {
        return users.values.asFlow().map { it.also { delay(1000) } }
    }

}