package io.primeascpect.kotlindemo.springintegration

import org.springframework.context.annotation.Bean
import org.springframework.integration.core.MessageSelector
import org.springframework.integration.core.MessageSource
import org.springframework.integration.dsl.*
import org.springframework.messaging.Message
import org.springframework.messaging.support.GenericMessage
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Consumer

class SpringIntegrationWithExtensionsConf {

    val integerSource = AtomicInteger()

    @Bean
    fun withExtensions(): IntegrationFlow {
        return fromSupplier(integerSource::incrementAndGet, 100L)
            .run { println("really important message") }
            .addHeader("key", "value")
            .doFilter(String::isNotEmpty, "discardChannel")
            .log()
            .get()
    }
}

fun fromSupplier(supplier: () -> Any, fixedRate: Long) = IntegrationFlows.from(
    MessageSource { GenericMessage(supplier) },
    Consumer { c: SourcePollingChannelAdapterSpec ->
        c.poller(
            Pollers.fixedRate(fixedRate)
        )
    }
)

fun <B: IntegrationFlowDefinition<B>> IntegrationFlowDefinition<B>.run(runnable: () -> Unit): IntegrationFlowDefinition<B> {
    return this.transform { _: Any -> runnable() }
}

fun EnricherSpec.addHeader(name: String, value: Any?): EnricherSpec {
    return value?.let { header(name, value) } ?: this
}

fun <T: IntegrationFlowDefinition<T>> IntegrationFlowDefinition<T>.addHeader(name: String, value: Any?): IntegrationFlowDefinition<T> {
    return this.enrich { it.addHeader(name, value) }
}

fun <P, B: IntegrationFlowDefinition<B>> IntegrationFlowDefinition<B>.doFilter(
    predicate: (P) -> Boolean,
    discardChannel: String
): IntegrationFlowDefinition<B> {
    return this.filter (
        MessageSelector { predicate((it as Message<P>).payload) }
    ) { fs: FilterEndpointSpec -> fs.discardChannel(discardChannel) }
}