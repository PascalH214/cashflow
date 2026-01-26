package de.pascalh214.cashflow.features.user.api.dtos;

import de.pascalh214.cashflow.features.user.application.UserInformationResult;
import de.pascalh214.cashflow.features.user.domain.UserId;

import java.util.List;

public record UserInformationResponse(
        UserId id,
        List<UserInformationResult.AccountSummary> accounts
) {
}
