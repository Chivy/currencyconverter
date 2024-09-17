package pl.maciej.jelen.currencyconverter.application.accounts

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import pl.maciej.jelen.currencyconverter.application.accounts.api.AccountResponse
import pl.maciej.jelen.currencyconverter.application.accounts.api.CreateAccountRequest
import pl.maciej.jelen.currencyconverter.common.api.Amount
import pl.maciej.jelen.currencyconverter.common.api.Currency
import pl.maciej.jelen.currencyconverter.domain.accounts.AccountsFacade
import java.math.BigDecimal
import java.util.*

@SpringBootTest
class AccountsControllerTest {

    @MockBean
    private lateinit var accountsFacade: AccountsFacade

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    private lateinit var webTestClient: WebTestClient

    @BeforeEach
    fun setup() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
            .configureClient()
            .baseUrl("/accounts")
            .build()
    }


    @Test
    fun `create account should return 201 CREATED`() {
        val createAccountRequest = CreateAccountRequest(
            name = "Maciej",
            surname = "Jelen",
            initialBalance = Amount(
                currency = Currency.PLN,
                value = BigDecimal.TEN
            )
        )

        val accountResponse = AccountResponse(UUID.randomUUID())

        given(accountsFacade.createAccount(createAccountRequest)).willReturn(accountResponse)

        webTestClient.post()
            .bodyValue(createAccountRequest)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().location("/accounts/${accountResponse.id}")
            .expectBody<AccountResponse>().isEqualTo(accountResponse)
    }
}