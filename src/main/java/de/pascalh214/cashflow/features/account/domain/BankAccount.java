package de.pascalh214.cashflow.features.account.domain;

import de.pascalh214.cashflow.features.user.domain.UserId;
import lombok.Getter;

@Getter
public class BankAccount extends Account {

    final private CountryCode countryCode;
    final private int checkDigits;
    final private String basicBankAccountNumber;

    private BankAccount(
            AccountId id,
            UserId userId,

            CountryCode countryCode,
            int checkDigits,
            String basicBankAccountNumber
    ) {
        super(id, userId, AccountType.BANK);

        this.countryCode = countryCode;
        this.checkDigits = checkDigits;
        this.basicBankAccountNumber = basicBankAccountNumber;
    }

    protected static BankAccount rehydrate(
            AccountId id,
            UserId userId,
            CountryCode countryCode,
            int checkDigits,
            String basicBankAccountNumber
    ) {
        return new BankAccount(
            id,
            userId,
            countryCode,
            checkDigits,
            basicBankAccountNumber
        );
    }

    public static BankAccount addAccount(
            AccountId id,
            UserId userId,

            CountryCode countryCode,
            int checkDigits,
            String basicBankAccountNumber
    ) {
        return new BankAccount(
                id,
                userId,
                countryCode,
                checkDigits,
                basicBankAccountNumber
        );
    }

}
