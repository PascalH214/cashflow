package de.pascalh214.cashflow.features.account.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateCreditCardAccountRequest(
        @NotBlank String cardNumber
) {
}
