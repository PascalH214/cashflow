package de.pascalh214.cashflow.features.account.domain;

import de.pascalh214.cashflow.common.domain.AggregateRoot;
import de.pascalh214.cashflow.features.transaction.domain.Transaction;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class Account extends AggregateRoot {

    final private AccountId id;
    final private AccountType type;
    final private List<Transaction> transactions = List.of();

    protected Account(
            AccountId id,
            AccountType type
    ) {
        this.id = id;
        this.type = type;
    }
}
