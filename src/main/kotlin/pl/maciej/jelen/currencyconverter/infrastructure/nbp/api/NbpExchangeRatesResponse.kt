package pl.maciej.jelen.currencyconverter.infrastructure.nbp.api

import java.math.BigDecimal
import java.time.LocalDate

data class NbpExchangeRatesResponse(
    val table: String,
    val currency: String,
    val code: String,
    val rates: List<Rate>
) {

    data class Rate(
        val no: String,
        val effectiveDate: LocalDate,
        val mid: BigDecimal
    )
}
