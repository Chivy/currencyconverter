package pl.maciej.jelen.currencyconverter.infrastructure.db

import org.junit.jupiter.api.Test
import pl.maciej.jelen.currencyconverter.common.api.Amount
import pl.maciej.jelen.currencyconverter.common.api.Currency
import pl.maciej.jelen.currencyconverter.domain.accounts.Account
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InMemoryAccountsRepositoryTest {

    private val subject = InMemoryAccountsRepository()

    @Test
    fun `should save to in-memory database and return saved account`() {
        val account = Account(
            UUID.randomUUID(),
            "Maciej",
            "Jelen",
            Amount(
                Currency.PLN,
                BigDecimal.TEN
            )
        )

        val result = subject.save(account)

        assertEquals(account, result)
        assertEquals(account, subject.findBy(account.id))
    }

    @Test
    fun `when account is present in database, isAccountRegistered should return true`() {
        val account = Account(
            UUID.randomUUID(),
            "Maciej",
            "Jelen",
            Amount(
                Currency.PLN,
                BigDecimal.TEN
            )
        )

        subject.save(account)

        assertTrue {
            subject.isAccountRegistered(account.id)
        }
    }

    @Test
    fun `isAccountRegistered should return false when not found in database`() {
        assertFalse { subject.isAccountRegistered(UUID.randomUUID()) }
    }
}