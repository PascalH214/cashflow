package de.pascalh214.cashflow.features.account.domain;

import lombok.Getter;

@Getter
public class BankAccount extends Account {

    final private CountryCode countryCode;
    final private int checkDigits;
    final private String basicBankAccountNumber;

    private BankAccount(
            AccountId id,
            AccountType type,

            CountryCode countryCode,
            int checkDigits,
            String basicBankAccountNumber
    ) {
        super(id, type);

        this.countryCode = countryCode;
        this.checkDigits = checkDigits;
        this.basicBankAccountNumber = basicBankAccountNumber;
    }

    public static BankAccount addAccount(
            AccountId id,
            AccountType type,

            CountryCode countryCode,
            int checkDigits,
            String basicBankAccountNumber
    ) {
        return new BankAccount(
                id,
                type,
                countryCode,
                checkDigits,
                basicBankAccountNumber
        );
    }

}
