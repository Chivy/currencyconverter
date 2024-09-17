package pl.maciej.jelen.currencyconverter.domain.accounts

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito.mock
import org.springframework.util.IdGenerator
import pl.maciej.jelen.currencyconverter.application.accounts.api.CreateAccountRequest
import pl.maciej.jelen.currencyconverter.common.api.Amount
import pl.maciej.jelen.currencyconverter.common.api.Currency
import pl.maciej.jelen.currencyconverter.infrastructure.db.InMemoryAccountsRepository
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AccountsFacadeTest {

    private val idGenerator = mock<IdGenerator>()
    private val accountsRepository = InMemoryAccountsRepository()

    private val subject = AccountsFacade(
        AccountsService(
            accountsRepository,
            idGenerator
        )
    )

    @Test
    fun `should create account`() {
        val accountId = UUID.randomUUID()
        val createAccountRequest = CreateAccountRequest(
            name = "Maciej",
            surname = "Jelen",
            initialBalance = Amount(
                currency = Currency.PLN,
                value = BigDecimal.TEN
            )
        )

        given(idGenerator.generateId()).willReturn(accountId)

        val result = subject.createAccount(createAccountRequest)

        assertEquals(accountId, result.id)
        assertTrue { accountsRepository.isAccountRegistered(accountId) }
    }
}