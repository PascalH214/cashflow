package de.pascalh214.cashflow.features.user.application;

import de.pascalh214.cashflow.features.user.domain.UserId;

public sealed interface UserInformationResult permits UserInformationResult.Success, UserInformationResult.Failure {
    record Success(
            UserId id
    ) implements UserInformationResult {}
    record Failure(
            String message
    ) implements UserInformationResult {}
}
