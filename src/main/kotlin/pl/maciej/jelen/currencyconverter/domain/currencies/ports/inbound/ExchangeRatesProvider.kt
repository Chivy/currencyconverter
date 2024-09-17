package pl.maciej.jelen.currencyconverter.domain.currencies.ports.inbound

import pl.maciej.jelen.currencyconverter.common.api.Currency
import java.math.BigDecimal

interface ExchangeRatesProvider {

    suspend fun getConversionRate(targetCurrency: Currency): BigDecimal

}
