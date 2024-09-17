package pl.maciej.jelen.currencyconverter.application.accounts.api

import pl.maciej.jelen.currencyconverter.common.api.Amount

data class CreateAccountRequest(
    val name: String,
    val surname: String,
    val initialBalance: Amount
) {

}
