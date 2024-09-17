package pl.maciej.jelen.currencyconverter.common.api

enum class DomainError(
    val code: Code,
    val message: String
) {

    ACCOUNT_REGISTRATION_REQUIRED(Code.VALIDATION_ERROR, "Account registration is required to use currency conversion API");

    enum class Code {
        VALIDATION_ERROR
    }
}
