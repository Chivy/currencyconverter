package pl.maciej.jelen.currencyconverter.application.currencies

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.maciej.jelen.currencyconverter.application.currencies.api.ConversionRequest
import pl.maciej.jelen.currencyconverter.application.currencies.api.ConversionResponse
import pl.maciej.jelen.currencyconverter.domain.currencies.CurrencyConversionsFacade
import java.util.UUID

@RestController
@RequestMapping("/currency-conversions")
class CurrencyConversionsController(private val currencyConversionsFacade: CurrencyConversionsFacade) {

    @PostMapping
    suspend fun convert(
        @RequestHeader(value = "X-ACCOUNT-ID") accountId: UUID,
        @RequestBody conversionRequest: ConversionRequest
    ): ResponseEntity<ConversionResponse> =
        ResponseEntity.ok(currencyConversionsFacade.convert(accountId, conversionRequest))
}