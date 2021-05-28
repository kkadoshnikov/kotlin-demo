package io.primeascpect.kotlindemo.springintegration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.core.MessageSelector
import org.springframework.integration.core.MessageSource
import org.springframework.integration.dsl.*
import org.springframework.messaging.Message
import org.springframework.messaging.support.GenericMessage
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

//@Configuration
class RawSpringIntegrationConf {

    val integerSource = AtomicInteger()

    @Bean
    fun withoutExtensions(): IntegrationFlow {
        return IntegrationFlows
            .from(
                MessageSource { GenericMessage(integerSource.incrementAndGet()) },
                Consumer { c: SourcePollingChannelAdapterSpec ->
                    c.poller(
                        Pollers.fixedRate(100)
                    )
                })
            .transform<Int, Int> { it.also { println("really important message") } }
            .enrich { it.header("key", "value") }
            .filter (
                MessageSelector { (it as Message<String>).payload.isNotEmpty() }
            ) { fs: FilterEndpointSpec -> fs.discardChannel("discardChannel") }
            .log()
            .get()
    }
}