package pl.maciej.jelen.currencyconverter.infrastructure.nbp

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "nbp.client")
data class NbpProperties(
    val baseUrl: String
)
