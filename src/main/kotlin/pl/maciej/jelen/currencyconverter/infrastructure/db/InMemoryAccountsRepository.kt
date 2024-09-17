package pl.maciej.jelen.currencyconverter.infrastructure.db

import org.springframework.stereotype.Component
import pl.maciej.jelen.currencyconverter.domain.accounts.Account
import pl.maciej.jelen.currencyconverter.domain.accounts.ports.inbound.AccountsRepository
import pl.maciej.jelen.currencyconverter.domain.currencies.ports.inbound.AccountsProvider
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryAccountsRepository : AccountsRepository, AccountsProvider {

    private val databaseStore = ConcurrentHashMap<UUID, Account>()

    override fun save(account: Account): Account {
        databaseStore[account.id] = account
        return account
    }

    override fun isAccountRegistered(accountId: UUID): Boolean = databaseStore.containsKey(accountId)

    fun findBy(accountId: UUID) = databaseStore[accountId]
}