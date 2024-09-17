package pl.maciej.jelen.currencyconverter.application.currencies.api

import pl.maciej.jelen.currencyconverter.common.api.Amount

data class ConversionResponse(
    val convertedFrom: Amount,
    val conversionResult: Amount
)
