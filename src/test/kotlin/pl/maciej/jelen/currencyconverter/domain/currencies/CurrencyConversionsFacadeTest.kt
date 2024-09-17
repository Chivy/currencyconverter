package pl.maciej.jelen.currencyconverter.domain.currencies

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import pl.maciej.jelen.currencyconverter.application.currencies.api.ConversionRequest
import pl.maciej.jelen.currencyconverter.application.currencies.api.ConversionResponse
import pl.maciej.jelen.currencyconverter.common.api.Amount
import pl.maciej.jelen.currencyconverter.common.api.Currency
import pl.maciej.jelen.currencyconverter.domain.currencies.exceptions.AccountRequiredException
import pl.maciej.jelen.currencyconverter.domain.currencies.ports.inbound.AccountsProvider
import pl.maciej.jelen.currencyconverter.domain.currencies.ports.inbound.ExchangeRatesProvider
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals

class CurrencyConversionsFacadeTest {

    private val exchangeRatesProvider = mock<ExchangeRatesProvider>()
    private val accountsProvider = mock<AccountsProvider>()

    private val subject = CurrencyConversionsFacade(
        CurrencyConversionsService(
            exchangeRatesProvider, accountsProvider
        )
    )

    @Test
    fun `should convert PLN to USD`() = runBlocking {
        val accountId = UUID.randomUUID()
        val conversionRequest = ConversionRequest(
            from = Amount(
                currency = Currency.PLN,
                value = BigDecimal.TEN
            ),
            targetCurrency = Currency.USD
        )

        given(exchangeRatesProvider.getConversionRate(Currency.USD)).willReturn(BigDecimal.valueOf(2))
        given(accountsProvider.isAccountRegistered(accountId)).willReturn(true)

        val result = subject.convert(accountId, conversionRequest)

        assertEquals(
            ConversionResponse(
                convertedFrom = Amount(
                    currency = Currency.PLN,
                    value = BigDecimal.TEN
                ),
                conversionResult = Amount(
                    currency = Currency.USD,
                    value = BigDecimal.valueOf(20)
                )
            ),
            result
        )
    }

    @Test
    fun `should fail with AccountRequiredException when non-existent accountId is passed`(): Unit = runBlocking {
        val accountId = UUID.randomUUID()
        val conversionRequest = ConversionRequest(
            from = Amount(
                currency = Currency.PLN,
                value = BigDecimal.TEN
            ),
            targetCurrency = Currency.USD
        )

        given(accountsProvider.isAccountRegistered(accountId)).willReturn(false)

        assertThrows<AccountRequiredException> { subject.convert(accountId, conversionRequest) }
    }
}