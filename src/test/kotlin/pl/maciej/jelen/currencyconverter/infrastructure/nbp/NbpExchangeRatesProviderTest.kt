package pl.maciej.jelen.currencyconverter.infrastructure.nbp

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.util.TestSocketUtils
import org.springframework.web.reactive.function.client.WebClient
import pl.maciej.jelen.currencyconverter.common.api.Currency
import pl.maciej.jelen.currencyconverter.infrastructure.nbp.api.NbpExchangeRatesResponse
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.test.assertEquals

class NbpExchangeRatesProviderTest {

    private val nbpMockServer = MockWebServer()

    private val nbpProperties = NbpProperties(baseUrl = "http://localhost:$port")

    private val subject = NbpExchangeRatesProvider(NbpWebClientConfiguration(nbpProperties).nbpWebClient())

    private val objectMapper = jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @BeforeEach
    fun setup() {
        nbpMockServer.start(port)
    }

    @Test
    fun `should return target currency exchange rate to PLN`() = runBlocking {
        val targetCurrency = Currency.USD

        val nbpResponse = NbpExchangeRatesResponse(
            table = "A",
            currency = "dolar amerykański",
            code = targetCurrency.name,
            rates = listOf(
                NbpExchangeRatesResponse.Rate(
                    no = UUID.randomUUID().toString(),
                    effectiveDate = LocalDate.now(),
                    mid = BigDecimal.TEN
                )
            )
        )

        nbpMockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(nbpResponse))
        )

        val usdExchangeRateResult = subject.getConversionRate(targetCurrency = targetCurrency)

        val request = nbpMockServer.takeRequest()

        assertEquals(BigDecimal.TEN, usdExchangeRateResult)

        assertEquals("GET", request.method)
        assertEquals("/exchangerates/rates/A/USD", request.path.toString())
    }

    companion object {
        private val port = TestSocketUtils.findAvailableTcpPort()

    }
}