package de.pascalh214.cashflow.features.account.domain;

import de.pascalh214.cashflow.features.account.infrastructure.persistence.AccountEntity;

public class AccountMapper {

    public static Account toAccount(AccountEntity accountEntity) {
        AccountId id = new AccountId(accountEntity.getId());
        AccountType type = accountEntity.getType();

        return switch (type) {
            case BANK -> BankAccount.addAccount(
                    id,
                    type,
                    new CountryCode(accountEntity.getCountryCode()),
                    accountEntity.getCheckDigits(),
                    accountEntity.getBasicBankAccountNumber()
            );
            case CREDIT_CARD -> CreditCard.addAccount(id, type);
        };
    }

    public static AccountEntity toAccountEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setId(account.getId().value());
        accountEntity.setType(account.getType());

        if (account instanceof BankAccount bankAccount) {
            accountEntity.setCountryCode(bankAccount.getCountryCode().value());
            accountEntity.setCheckDigits(bankAccount.getCheckDigits());
            accountEntity.setBasicBankAccountNumber(bankAccount.getBasicBankAccountNumber());
        }

        return accountEntity;
    }

}
