package de.pascalh214.cashflow.features.account.application;

import de.pascalh214.cashflow.features.account.domain.AccountId;

public sealed interface CreateAccountResult {
    record Success(AccountId id) implements CreateAccountResult {}
    record Failure(String message) implements CreateAccountResult {}
}
