package de.pascalh214.cashflow.features.account.api.dtos;

public record CreateBankAccountRequest(
        String countryCode,
        int checkNumber,
        String basicBankAccountNumber
) {}
