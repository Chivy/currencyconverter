package pl.maciej.jelen.currencyconverter.domain.accounts

import org.springframework.stereotype.Component
import pl.maciej.jelen.currencyconverter.application.accounts.api.AccountResponse
import pl.maciej.jelen.currencyconverter.application.accounts.api.CreateAccountRequest

@Component
class AccountsFacade(private val accountsService: AccountsService) {

    fun createAccount(createAccountRequest: CreateAccountRequest): AccountResponse =
        accountsService.createAccount(createAccountRequest)
}
