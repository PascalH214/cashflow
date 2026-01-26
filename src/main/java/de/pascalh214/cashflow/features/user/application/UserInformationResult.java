package de.pascalh214.cashflow.features.user.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.pascalh214.cashflow.features.account.domain.AccountId;
import de.pascalh214.cashflow.features.account.domain.AccountType;
import de.pascalh214.cashflow.features.user.domain.UserId;

import java.util.List;

public sealed interface UserInformationResult permits UserInformationResult.Success, UserInformationResult.Failure {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    record Success(
            UserId id,
            List<AccountSummary> accounts
    ) implements UserInformationResult {}
    record Failure(
            String message
    ) implements UserInformationResult {}

    record AccountSummary(
            AccountId id,
            AccountType type,
            String countryCode,
            Integer checkDigits,
            String basicBankAccountNumber,
            String cardNumber
    ) {}
}
