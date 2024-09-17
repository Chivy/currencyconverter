package pl.maciej.jelen.currencyconverter.common.api

import java.math.BigDecimal

data class Amount(
    val currency: Currency,
    val value: BigDecimal
)