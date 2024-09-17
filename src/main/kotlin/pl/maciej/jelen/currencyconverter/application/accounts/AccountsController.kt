package pl.maciej.jelen.currencyconverter.application.accounts

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.maciej.jelen.currencyconverter.application.accounts.api.CreateAccountRequest
import pl.maciej.jelen.currencyconverter.application.accounts.api.AccountResponse
import pl.maciej.jelen.currencyconverter.domain.accounts.AccountsFacade
import java.net.URI

@RestController
@RequestMapping("/accounts")
class AccountsController(
    private val accountsFacade: AccountsFacade
) {

    @PostMapping
    suspend fun createAccount(@RequestBody createAccountRequest: CreateAccountRequest): ResponseEntity<AccountResponse> {
        val accountResponse = accountsFacade.createAccount(createAccountRequest)
        return ResponseEntity
            .created(URI.create("/accounts/${accountResponse.id}"))
            .body(accountResponse)
    }
}