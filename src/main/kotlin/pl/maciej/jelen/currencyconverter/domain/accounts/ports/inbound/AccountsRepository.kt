package pl.maciej.jelen.currencyconverter.domain.accounts.ports.inbound

import pl.maciej.jelen.currencyconverter.domain.accounts.Account

interface AccountsRepository {

    fun save(account: Account): Account

}
