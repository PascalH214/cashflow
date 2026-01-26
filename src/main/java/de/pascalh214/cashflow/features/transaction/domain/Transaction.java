package de.pascalh214.cashflow.features.transaction.domain;

import de.pascalh214.cashflow.common.domain.AggregateRoot;

import java.math.BigDecimal;

public class Transaction extends AggregateRoot {

    private TransactionId id;
    private BigDecimal amount;

    private Transaction(TransactionId id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public static Transaction createTransaction(TransactionId id, BigDecimal amount) {
        return new Transaction(id, amount);
    }

}
