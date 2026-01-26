package de.pascalh214.cashflow.features.account.domain;

public class CreditCard extends Account {

    private CreditCard(AccountId id, AccountType type) {
        super(id, type);
    }

    public static CreditCard addAccount(AccountId id, AccountType type) {
        return new CreditCard(id, type);
    }

}
