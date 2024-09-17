package pl.maciej.jelen.currencyconverter.domain.accounts

import pl.maciej.jelen.currencyconverter.application.accounts.api.AccountResponse
import pl.maciej.jelen.currencyconverter.application.accounts.api.CreateAccountRequest
import pl.maciej.jelen.currencyconverter.common.api.Amount
import pl.maciej.jelen.currencyconverter.common.api.Currency
import java.math.BigDecimal
import java.util.UUID

data class Account(
    val id: UUID,
    val name: String,
    val surname: String,
    val balance: Amount,
) {

    fun toResponse() = AccountResponse(id = this.id)

    companion object {

        fun create(id: UUID, createAccountRequest: CreateAccountRequest) = Account(
            id = id,
            name = createAccountRequest.name,
            surname = createAccountRequest.surname,
            balance = createAccountRequest.initialBalance
        )
    }
}
