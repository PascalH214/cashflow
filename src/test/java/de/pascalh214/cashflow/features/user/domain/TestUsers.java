package de.pascalh214.cashflow.features.user.domain;

import de.pascalh214.cashflow.features.account.domain.Account;

import java.time.Instant;
import java.util.List;

public final class TestUsers {

    private TestUsers() {
    }

    public static User rehydrateWithAccounts(
            UserId id,
            Instant createdAt,
            Instant updatedAt,
            List<Account> accounts
    ) {
        return User.rehydrate(id, createdAt, updatedAt, accounts);
    }
}
