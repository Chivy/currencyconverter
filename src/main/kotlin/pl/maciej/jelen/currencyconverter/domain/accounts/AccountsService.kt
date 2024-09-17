package pl.maciej.jelen.currencyconverter.domain.accounts

import org.springframework.stereotype.Service
import org.springframework.util.IdGenerator
import pl.maciej.jelen.currencyconverter.application.accounts.api.AccountResponse
import pl.maciej.jelen.currencyconverter.application.accounts.api.CreateAccountRequest
import pl.maciej.jelen.currencyconverter.domain.accounts.ports.inbound.AccountsRepository

@Service
class AccountsService(
    private val accountsRepository: AccountsRepository,
    private val accountIdGenerator: IdGenerator,
) {
    fun createAccount(createAccountRequest: CreateAccountRequest): AccountResponse =
        accountsRepository
            .save(Account.create(accountIdGenerator.generateId(), createAccountRequest))
            .toResponse()

}
