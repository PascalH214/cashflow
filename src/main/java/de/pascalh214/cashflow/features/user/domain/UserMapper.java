package de.pascalh214.cashflow.features.user.domain;

import de.pascalh214.cashflow.features.account.domain.Account;
import de.pascalh214.cashflow.features.account.domain.AccountMapper;
import de.pascalh214.cashflow.features.account.infrastructure.persistence.AccountEntity;
import de.pascalh214.cashflow.features.user.infrastructure.persistence.UserEntity;

import java.util.List;

public class UserMapper {

    public static UserEntity toUserEntity(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(user.getId().value());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setUpdatedAt(user.getUpdatedAt());
        List<AccountEntity> accounts = user.getAccounts() == null
                ? List.of()
                : user.getAccounts().stream()
                .map(AccountMapper::toAccountEntity)
                .toList();
        accounts.forEach(accountEntity -> accountEntity.setUser(userEntity));
        userEntity.setAccounts(accounts);

        return userEntity;
    }

    public static User toUser(UserEntity userEntity) {
        List<Account> accounts = userEntity.getAccounts() == null
                ? List.of()
                : userEntity.getAccounts().stream()
                .map(AccountMapper::toAccount)
                .toList();
        return User.rehydrate(
                new UserId(userEntity.getId()),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt(),
                accounts
        );
    }

}
