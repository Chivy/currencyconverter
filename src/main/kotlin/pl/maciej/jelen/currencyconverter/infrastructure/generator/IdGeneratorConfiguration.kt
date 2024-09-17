package pl.maciej.jelen.currencyconverter.infrastructure.generator

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.IdGenerator
import java.util.*

@Configuration
class IdGeneratorConfiguration {

    @Bean
    fun accountIdGenerator() = IdGenerator { UUID.randomUUID() }
}