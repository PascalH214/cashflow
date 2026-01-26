package de.pascalh214.cashflow.features.account.application;

import de.pascalh214.cashflow.features.account.domain.Account;
import de.pascalh214.cashflow.features.account.domain.AccountId;

import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);

    Optional<Account> findById(AccountId id);
}
