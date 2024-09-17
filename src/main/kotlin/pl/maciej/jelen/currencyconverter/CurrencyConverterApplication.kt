package pl.maciej.jelen.currencyconverter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import pl.maciej.jelen.currencyconverter.infrastructure.nbp.NbpProperties

@EnableConfigurationProperties(
    value = [
        NbpProperties::class
    ]
)
@SpringBootApplication
class CurrencyConverterApplication

fun main(args: Array<String>) {
    runApplication<CurrencyConverterApplication>(*args)
}
