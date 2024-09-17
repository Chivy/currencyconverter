package pl.maciej.jelen.currencyconverter.application.currencies

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import pl.maciej.jelen.currencyconverter.application.ErrorResponse
import pl.maciej.jelen.currencyconverter.application.currencies.api.ConversionRequest
import pl.maciej.jelen.currencyconverter.application.currencies.api.ConversionResponse
import pl.maciej.jelen.currencyconverter.common.api.Amount
import pl.maciej.jelen.currencyconverter.common.api.Currency
import pl.maciej.jelen.currencyconverter.common.api.DomainError
import pl.maciej.jelen.currencyconverter.domain.currencies.CurrencyConversionsFacade
import pl.maciej.jelen.currencyconverter.domain.currencies.exceptions.AccountRequiredException
import java.math.BigDecimal
import java.util.*

@SpringBootTest
class CurrencyConversionsControllerTest {

    @MockBean
    private lateinit var currencyConversionsFacade: CurrencyConversionsFacade

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setup() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
            .configureClient()
            .baseUrl("/currency-conversions")
            .build()
    }

    @Test
    fun `should convert PLN to USD`(): Unit = runBlocking {
        val accountId = UUID.randomUUID()

        val conversionRequest = ConversionRequest(
            from = Amount(
                currency = Currency.PLN,
                value = BigDecimal.TEN
            ),
            targetCurrency = Currency.USD
        )

        val conversionResponse = ConversionResponse(
            convertedFrom = Amount(
                currency = Currency.PLN,
                value = BigDecimal.TEN
            ),
            conversionResult = Amount(
                currency = Currency.USD,
                value = BigDecimal.valueOf(38.83)
            )
        )

        given(currencyConversionsFacade.convert(accountId, conversionRequest)).willReturn(conversionResponse)

        webTestClient.post()
            .header("X-ACCOUNT-ID", accountId.toString())
            .bodyValue(conversionRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody<ConversionResponse>().isEqualTo(conversionResponse)
    }

    @Test
    fun `when accountId is not passed, should result in 400 BAD_REQUEST`(): Unit = runBlocking {
        val conversionRequest = ConversionRequest(
            from = Amount(
                currency = Currency.PLN,
                value = BigDecimal.TEN
            ),
            targetCurrency = Currency.USD
        )


        webTestClient.post()
            .bodyValue(conversionRequest)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `when accountId is not found in database, should return 400 BAD_REQUEST with validation error`(): Unit =
        runBlocking {
            val accountId = UUID.randomUUID()

            val conversionRequest = ConversionRequest(
                from = Amount(
                    currency = Currency.PLN,
                    value = BigDecimal.TEN
                ),
                targetCurrency = Currency.USD
            )

            given(currencyConversionsFacade.convert(accountId, conversionRequest)).willThrow(AccountRequiredException())

            webTestClient.post()
                .header("X-ACCOUNT-ID", accountId.toString())
                .bodyValue(conversionRequest)
                .exchange()
                .expectStatus().isBadRequest
                .expectBody<ErrorResponse>().isEqualTo(
                    ErrorResponse(
                        DomainError.ACCOUNT_REGISTRATION_REQUIRED.code.name,
                        DomainError.ACCOUNT_REGISTRATION_REQUIRED.message,
                    )
                )
        }
}