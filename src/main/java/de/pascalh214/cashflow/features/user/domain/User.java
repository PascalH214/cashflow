package de.pascalh214.cashflow.features.user.domain;

import de.pascalh214.cashflow.common.domain.AggregateRoot;
import de.pascalh214.cashflow.features.account.domain.Account;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class User extends AggregateRoot {

    private final UserId id;
    private final Instant createdAt;
    private Instant updatedAt;

    private final List<Account> accounts;

    private User(
            UserId id,
            Instant createdAt,
            Instant updatedAt,

            List<Account> accounts
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.accounts = accounts;
    }

    protected static User rehydrate(
            UserId id,
            Instant createdAt,
            Instant updatedAt,

            List<Account> accounts
    ) {
        return new User(
                id,
                createdAt,
                updatedAt,
                accounts
        );
    }

    public static User createUser(Instant now, UserId id) {
        return new User(
                id,
                now, now,
                List.of()
        );
    }

}
