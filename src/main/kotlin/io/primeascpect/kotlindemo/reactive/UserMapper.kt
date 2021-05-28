package io.primeascpect.kotlindemo.reactive

import io.primeascpect.kotlindemo.Mapper
import org.springframework.stereotype.Component

@Component
class UserMapper: Mapper<User, UserDTO> {
    override fun UserDTO.mapToEntity() = User(id, name, age)

    override fun User.mapToDto() = UserDTO(id, name, age)

}
