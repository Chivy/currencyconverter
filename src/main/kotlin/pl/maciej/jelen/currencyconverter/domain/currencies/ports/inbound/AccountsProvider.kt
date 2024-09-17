package pl.maciej.jelen.currencyconverter.domain.currencies.ports.inbound

import java.util.UUID

interface AccountsProvider {

    fun isAccountRegistered(accountId: UUID): Boolean
}
