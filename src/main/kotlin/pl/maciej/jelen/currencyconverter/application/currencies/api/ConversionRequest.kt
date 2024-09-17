package pl.maciej.jelen.currencyconverter.application.currencies.api

import pl.maciej.jelen.currencyconverter.common.api.Amount
import pl.maciej.jelen.currencyconverter.common.api.Currency


data class ConversionRequest(
    val from: Amount,
    val targetCurrency: Currency
)
