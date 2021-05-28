package io.primeascpect.kotlindemo.reactive

import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import kotlin.random.Random

@Component
class UserGenerator(private val nameClient: NameClient) {

    suspend fun generate(id: Int): User {
        return User(id, nameClient.getName(), Random.nextInt(1, 100))
    }
}

@Component
class NameClient {
    private val webClient = WebClient.create("http://names.drycodes.com/")

    suspend fun getName(): String = webClient.get()
        .uri {
            it.path("1").queryParam("nameOptions", "presidents").build()
        }.retrieve()
        .toEntity<List<String>>()
        .awaitFirst()
        .body!!
        .first()
}
