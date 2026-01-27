package de.pascalh214.cashflow.features.user.domain;

import de.pascalh214.cashflow.features.account.domain.Account;
import de.pascalh214.cashflow.features.account.domain.AccountId;
import de.pascalh214.cashflow.features.account.domain.AccountType;
import de.pascalh214.cashflow.features.account.domain.BankAccount;
import de.pascalh214.cashflow.features.account.domain.CountryCode;
import de.pascalh214.cashflow.features.account.domain.CreditCard;
import de.pascalh214.cashflow.features.account.infrastructure.persistence.AccountEntity;
import de.pascalh214.cashflow.features.account.infrastructure.persistence.BankAccountEntity;
import de.pascalh214.cashflow.features.account.infrastructure.persistence.CreditCardEntity;
import de.pascalh214.cashflow.features.user.infrastructure.persistence.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void mapsUserToEntityWithAccounts() {
        UserId userId = new UserId(UUID.randomUUID());
        BankAccount bankAccount = BankAccount.addAccount(
                new AccountId(UUID.randomUUID()),
                userId,
                new CountryCode("DE"),
                12,
                "1234567890"
        );
        CreditCard creditCard = CreditCard.addAccount(
                new AccountId(UUID.randomUUID()),
                userId,
                "4111111111111111"
        );
        Instant now = Instant.now();
        User user = TestUsers.rehydrateWithAccounts(
                userId,
                now,
                now,
                List.of(bankAccount, creditCard)
        );

        UserEntity entity = UserMapper.toUserEntity(user);

        assertThat(entity.getId()).isEqualTo(userId.value());
        assertThat(entity.getAccounts()).hasSize(2);
        assertThat(entity.getAccounts()).allSatisfy(account -> {
            assertThat(account.getUser()).isEqualTo(entity);
        });
    }

    @Test
    void mapsEntityToUserWithAccounts() {
        UserEntity userEntity = new UserEntity();
        UUID userId = UUID.randomUUID();
        userEntity.setId(userId);
        userEntity.setCreatedAt(Instant.now());
        userEntity.setUpdatedAt(Instant.now());

        BankAccountEntity bankEntity = new BankAccountEntity();
        bankEntity.setId(UUID.randomUUID());
        bankEntity.setUser(userEntity);
        bankEntity.setType(AccountType.BANK);
        bankEntity.setCountryCode("DE");
        bankEntity.setCheckDigits(12);
        bankEntity.setBasicBankAccountNumber("1234567890");

        CreditCardEntity creditEntity = new CreditCardEntity();
        creditEntity.setId(UUID.randomUUID());
        creditEntity.setUser(userEntity);
        creditEntity.setType(AccountType.CREDIT_CARD);
        creditEntity.setCardNumber("4111111111111111");

        userEntity.setAccounts(List.of(bankEntity, creditEntity));

        User user = UserMapper.toUser(userEntity);

        assertThat(user.getId()).isEqualTo(new UserId(userId));
        List<Account> accounts = user.getAccounts();
        assertThat(accounts).hasSize(2);
        assertThat(accounts).anySatisfy(account -> {
            assertThat(account.getType()).isEqualTo(AccountType.BANK);
        });
        assertThat(accounts).anySatisfy(account -> {
            assertThat(account.getType()).isEqualTo(AccountType.CREDIT_CARD);
        });
    }
}
