package de.pascalh214.cashflow.features.account.domain;

import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.user.domain.UserId;
import lombok.Getter;

@Getter
public class BankAccount extends Account {

    final private Country country;
    final private int checkDigits;
    final private String basicBankAccountNumber;
    final private String iban;

    private BankAccount(
            AccountId id,
            UserId userId,

            Country country,
            int checkDigits,
            String basicBankAccountNumber,
            String iban
    ) {
        super(id, userId, AccountType.BANK);

        this.country = country;
        this.checkDigits = checkDigits;
        this.basicBankAccountNumber = basicBankAccountNumber;
        this.iban = iban;
    }

    protected static BankAccount rehydrate(
            AccountId id,
            UserId userId,
            Country country,
            int checkDigits,
            String basicBankAccountNumber,
            String iban
    ) {
        return new BankAccount(
            id,
            userId,
            country,
            checkDigits,
            basicBankAccountNumber,
            iban
        );
    }

    public static BankAccount addAccount(
            AccountId id,
            UserId userId,

            Country country,
            int checkDigits,
            String basicBankAccountNumber,
            String iban
    ) {
        return new BankAccount(
                id,
                userId,
                country,
                checkDigits,
                basicBankAccountNumber,
                iban
        );
    }

}
