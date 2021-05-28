package io.primeascpect.kotlindemo

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.*

abstract class ControllerTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var objectMapper: ObjectMapper

    protected inline fun <reified T : Any> sendPostRequest(
        body: Any? = null,
        path: String,
        crossinline should: MockMvcResultMatchersDsl.() -> Unit = {}
    ): T {
        val response = mockMvc.post(path) {
            body?.let {
                contentType = MediaType.APPLICATION_JSON
                content = it.toJson()
            }
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            should(this)
        }.andReturn().response
        if (T::class.java == Unit::class.java) {
            return Unit as T
        }
//        return objectMapper.readValue(response.contentAsByteArray)
        return 1 as T
    }

    protected fun Any.toJson() = objectMapper.writeValueAsString(this)
}

@SpringBootTest
class MyEndpointTest : ControllerTest() {

    @Test
    fun myTest() {
        val dto = sendPostRequest<Dto>(mapOf("key" to "value"), "path") {
            header { exists("Authorization") }
        }

        dto.apply {
            field shouldBe "10"
            field2 shouldBe 3
        }
    }
}

data class Dto(val field: String, val field2: Int)