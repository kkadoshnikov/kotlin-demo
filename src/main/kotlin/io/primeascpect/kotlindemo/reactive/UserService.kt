package io.primeascpect.kotlindemo.reactive

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

interface UserService {
    suspend fun getById(id: Int): User

    fun getAll(): Flow<User>
}

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val userGenerator: UserGenerator): UserService {

    init {
//        runBlocking { (1..10).forEach { generateAndSaveUser(it) } }

        GlobalScope.launch {
            (1..10).forEach { generateAndSaveUser(it) }
        }
    }

    override suspend fun getById(id: Int): User {
        return userRepository.getById(id)
            ?: generateAndSaveUser(id)
    }

    override fun getAll(): Flow<User> {
        return userRepository.getAll()
    }

    // без suspend будет ошибка
    private suspend fun generateAndSaveUser(id: Int): User {
        val user = userGenerator.generate(id)
        userRepository.save(user)
        return user
    }
}