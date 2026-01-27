package de.pascalh214.cashflow.features.account.domain;

import de.pascalh214.cashflow.features.account.infrastructure.persistence.AccountEntity;
import de.pascalh214.cashflow.features.account.infrastructure.persistence.BankAccountEntity;
import de.pascalh214.cashflow.features.account.infrastructure.persistence.CreditCardEntity;
import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.country.domain.CountryId;
import de.pascalh214.cashflow.features.country.infrastructure.persistence.CountryEntity;
import de.pascalh214.cashflow.features.user.domain.UserId;
import de.pascalh214.cashflow.features.user.infrastructure.persistence.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AccountMapperTest {

    @Test
    void mapsBankAccountEntityToDomain() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        BankAccountEntity entity = new BankAccountEntity();
        entity.setId(UUID.randomUUID());
        entity.setUser(userEntity);
        entity.setType(AccountType.BANK);
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setAlpha2Code("DE");
        countryEntity.setName("Germany");
        entity.setCountry(countryEntity);
        entity.setCheckDigits(12);
        entity.setBasicBankAccountNumber("1234567890");
        entity.setIban("DE121234567890");

        Account account = AccountMapper.toAccount(entity);

        assertThat(account).isInstanceOf(BankAccount.class);
        BankAccount bankAccount = (BankAccount) account;
        assertThat(bankAccount.getUserId()).isEqualTo(new UserId(userEntity.getId()));
        assertThat(bankAccount.getCountry().getId()).isEqualTo(new CountryId("DE"));
        assertThat(bankAccount.getCheckDigits()).isEqualTo(12);
        assertThat(bankAccount.getBasicBankAccountNumber()).isEqualTo("1234567890");
        assertThat(bankAccount.getIban()).isEqualTo("DE121234567890");
    }

    @Test
    void mapsCreditCardEntityToDomain() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        CreditCardEntity entity = new CreditCardEntity();
        entity.setId(UUID.randomUUID());
        entity.setUser(userEntity);
        entity.setType(AccountType.CREDIT_CARD);
        entity.setCardNumber("4111111111111111");

        Account account = AccountMapper.toAccount(entity);

        assertThat(account).isInstanceOf(CreditCard.class);
        CreditCard creditCard = (CreditCard) account;
        assertThat(creditCard.getUserId()).isEqualTo(new UserId(userEntity.getId()));
        assertThat(creditCard.getCardNumber()).isEqualTo("4111111111111111");
    }

    @Test
    void mapsBankAccountToEntity() {
        UserId userId = new UserId(UUID.randomUUID());
        BankAccount bankAccount = BankAccount.addAccount(
                new AccountId(UUID.randomUUID()),
                userId,
                Country.rehydrate(new CountryId("DE"), "Germany", List.of(), List.of()),
                12,
                "1234567890",
                "DE121234567890"
        );

        AccountEntity entity = AccountMapper.toAccountEntity(bankAccount);

        assertThat(entity).isInstanceOf(BankAccountEntity.class);
        BankAccountEntity bankEntity = (BankAccountEntity) entity;
        assertThat(bankEntity.getType()).isEqualTo(AccountType.BANK);
        assertThat(bankEntity.getCheckDigits()).isEqualTo(12);
        assertThat(bankEntity.getBasicBankAccountNumber()).isEqualTo("1234567890");
        assertThat(bankEntity.getIban()).isEqualTo("DE121234567890");
    }

    @Test
    void mapsCreditCardToEntity() {
        UserId userId = new UserId(UUID.randomUUID());
        CreditCard creditCard = CreditCard.addAccount(
                new AccountId(UUID.randomUUID()),
                userId,
                "4111111111111111"
        );

        AccountEntity entity = AccountMapper.toAccountEntity(creditCard);

        assertThat(entity).isInstanceOf(CreditCardEntity.class);
        CreditCardEntity creditEntity = (CreditCardEntity) entity;
        assertThat(creditEntity.getType()).isEqualTo(AccountType.CREDIT_CARD);
        assertThat(creditEntity.getCardNumber()).isEqualTo("4111111111111111");
    }
}
