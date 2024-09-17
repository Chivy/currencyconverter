package pl.maciej.jelen.currencyconverter.domain.currencies

import org.springframework.stereotype.Service
import pl.maciej.jelen.currencyconverter.application.currencies.api.ConversionRequest
import pl.maciej.jelen.currencyconverter.application.currencies.api.ConversionResponse
import pl.maciej.jelen.currencyconverter.common.api.Amount
import pl.maciej.jelen.currencyconverter.domain.currencies.exceptions.AccountRequiredException
import pl.maciej.jelen.currencyconverter.domain.currencies.ports.inbound.AccountsProvider
import pl.maciej.jelen.currencyconverter.domain.currencies.ports.inbound.ExchangeRatesProvider
import java.util.*

@Service
class CurrencyConversionsService(
    private val exchangeRatesProvider: ExchangeRatesProvider,
    private val accountsProvider: AccountsProvider
) {

    suspend fun convert(accountId: UUID, conversionRequest: ConversionRequest): ConversionResponse {
        if (!accountsProvider.isAccountRegistered(accountId)) throw AccountRequiredException()

        val conversionRate = exchangeRatesProvider.getConversionRate(targetCurrency = conversionRequest.targetCurrency)

        return ConversionResponse(
            convertedFrom = conversionRequest.from,
            conversionResult = Amount(
                currency = conversionRequest.targetCurrency,
                value = conversionRequest.from.value * conversionRate
            )
        )
    }


}
