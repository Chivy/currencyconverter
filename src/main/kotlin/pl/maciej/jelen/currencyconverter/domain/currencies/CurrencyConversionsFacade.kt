package pl.maciej.jelen.currencyconverter.domain.currencies

import org.springframework.stereotype.Component
import pl.maciej.jelen.currencyconverter.application.currencies.api.ConversionRequest
import pl.maciej.jelen.currencyconverter.application.currencies.api.ConversionResponse
import java.util.*

@Component
class CurrencyConversionsFacade(private val currencyConversionsService: CurrencyConversionsService) {

    suspend fun convert(accountId: UUID, conversionRequest: ConversionRequest): ConversionResponse =
        currencyConversionsService.convert(accountId, conversionRequest)

}
