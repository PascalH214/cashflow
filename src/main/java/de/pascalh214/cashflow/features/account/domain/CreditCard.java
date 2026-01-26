package de.pascalh214.cashflow.features.account.domain;

import de.pascalh214.cashflow.features.user.domain.UserId;
import lombok.Getter;

@Getter
public class CreditCard extends Account {

    private final String cardNumber;

    private CreditCard(AccountId id, UserId userId, String cardNumber) {
        super(id, userId, AccountType.CREDIT_CARD);

        this.cardNumber = cardNumber;
    }

    protected static CreditCard rehydrate(
            AccountId id,
            UserId userId,
            String cardNumber
    ) {
        return new CreditCard(id, userId, cardNumber);
    }

    public static CreditCard addAccount(
            AccountId id,
            UserId userId,
            String cardNumber
    ) {
        return new CreditCard(id, userId, cardNumber);
    }

}
