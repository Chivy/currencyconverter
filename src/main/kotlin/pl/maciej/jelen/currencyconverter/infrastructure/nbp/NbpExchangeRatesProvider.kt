package pl.maciej.jelen.currencyconverter.infrastructure.nbp

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import pl.maciej.jelen.currencyconverter.common.api.Currency
import pl.maciej.jelen.currencyconverter.domain.currencies.ports.inbound.ExchangeRatesProvider
import pl.maciej.jelen.currencyconverter.infrastructure.nbp.api.NbpExchangeRatesResponse
import java.math.BigDecimal

@Component
class NbpExchangeRatesProvider(private val nbpWebClient: WebClient) : ExchangeRatesProvider {

    override suspend fun getConversionRate(targetCurrency: Currency): BigDecimal =
        nbpWebClient.get()
            .uri("/exchangerates/rates/A/${targetCurrency.name}")
            .retrieve()
            .awaitBody<NbpExchangeRatesResponse>()
            .rates
            .first()
            .mid
}