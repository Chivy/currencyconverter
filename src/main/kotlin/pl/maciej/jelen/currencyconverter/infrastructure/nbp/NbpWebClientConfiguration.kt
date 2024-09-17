package pl.maciej.jelen.currencyconverter.infrastructure.nbp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class NbpWebClientConfiguration(
    private val nbpProperties: NbpProperties
) {

    @Bean
    fun nbpWebClient(): WebClient =
        WebClient.builder()
            .baseUrl(nbpProperties.baseUrl)
            .build()
}