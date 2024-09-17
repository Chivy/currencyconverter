package pl.maciej.jelen.currencyconverter.application

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import pl.maciej.jelen.currencyconverter.common.api.DomainError
import pl.maciej.jelen.currencyconverter.domain.currencies.exceptions.AccountRequiredException

@RestControllerAdvice
class CurrencyConverterExceptionHandler {

    @ExceptionHandler(AccountRequiredException::class)
    fun handleAccountRequiredException(ex: AccountRequiredException): ResponseEntity<ErrorResponse> =
        ResponseEntity(
            ErrorResponse(
                DomainError.ACCOUNT_REGISTRATION_REQUIRED.code.name,
                DomainError.ACCOUNT_REGISTRATION_REQUIRED.message
            ),
            HttpStatus.BAD_REQUEST
        )
}