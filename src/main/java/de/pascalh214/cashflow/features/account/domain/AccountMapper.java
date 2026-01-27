package de.pascalh214.cashflow.features.account.domain;

import de.pascalh214.cashflow.features.account.infrastructure.persistence.AccountEntity;
import de.pascalh214.cashflow.features.account.infrastructure.persistence.BankAccountEntity;
import de.pascalh214.cashflow.features.account.infrastructure.persistence.CreditCardEntity;
import de.pascalh214.cashflow.features.country.domain.CountryMapper;
import de.pascalh214.cashflow.features.user.domain.UserId;
import org.jspecify.annotations.NonNull;

public class AccountMapper {

    public static Account toAccount(AccountEntity accountEntity) {
        AccountType type = accountEntity.getType();

        return switch (type) {
            case BANK -> getBankAccount((BankAccountEntity) accountEntity);
            case CREDIT_CARD -> getCreditCard((CreditCardEntity) accountEntity);
        };

    }

    public static AccountEntity toAccountEntity(Account account) {
        return switch (account.getType()) {
            case BANK -> getBankAccountEntity((BankAccount) account);
            case CREDIT_CARD -> getCreditCardEntity((CreditCard) account);
        };

    }

    private static @NonNull BankAccountEntity getBankAccountEntity(BankAccount account) {
        BankAccountEntity bankAccountEntity = new BankAccountEntity();

        bankAccountEntity.setId(account.getId().value());
        bankAccountEntity.setType(account.getType());
        bankAccountEntity.setCheckDigits(account.getCheckDigits());
        bankAccountEntity.setBasicBankAccountNumber(account.getBasicBankAccountNumber());
        bankAccountEntity.setIban(account.getIban());

        return bankAccountEntity;
    }

    private static @NonNull BankAccount getBankAccount(BankAccountEntity account) {
        return BankAccount.rehydrate(
                new AccountId(account.getId()),
                new UserId(account.getUser().getId()),
                CountryMapper.toCountry(account.getCountry()),
                account.getCheckDigits(),
                account.getBasicBankAccountNumber(),
                account.getIban()
        );
    }

    private static @NonNull CreditCardEntity getCreditCardEntity(CreditCard creditCard) {
        CreditCardEntity creditCardEntity = new CreditCardEntity();

        creditCardEntity.setId(creditCard.getId().value());
        creditCardEntity.setType(creditCard.getType());
        creditCardEntity.setCardNumber(creditCard.getCardNumber());

        return creditCardEntity;
    }

    private static @NonNull CreditCard getCreditCard(CreditCardEntity account) {
        return CreditCard.rehydrate(
                new AccountId(account.getId()),
                new UserId(account.getUser().getId()),
                account.getCardNumber()
        );
    }

}
