package io.primeascpect.kotlindemo.reactive

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class ReactiveEndpoint(private val userService: UserService, private val userMapper: UserMapper) {

    @GetMapping("/{id}")
    suspend fun getById(@PathVariable("id") id: Int): UserDTO {
        return userService.getById(id).let(userMapper::toDto)
    }

    @GetMapping(produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getAll(): Flow<UserDTO> {
        return userService.getAll().map(userMapper::toDto)
    }
}