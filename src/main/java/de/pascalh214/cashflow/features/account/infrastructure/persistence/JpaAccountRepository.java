package de.pascalh214.cashflow.features.account.infrastructure.persistence;

import de.pascalh214.cashflow.features.account.application.AccountRepository;
import de.pascalh214.cashflow.features.account.domain.Account;
import de.pascalh214.cashflow.features.account.domain.AccountId;
import de.pascalh214.cashflow.features.account.domain.AccountMapper;
import de.pascalh214.cashflow.features.user.infrastructure.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaAccountRepository implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = AccountMapper.toAccountEntity(account);
        accountEntity.setUser(userJpaRepository.getReferenceById(account.getUserId().value()));
        AccountEntity savedEntity = this.accountJpaRepository.save(accountEntity);
        return AccountMapper.toAccount(savedEntity);
    }

    @Override
    public Optional<Account> findById(AccountId id) {
        return this.accountJpaRepository.findById(id.value())
                .map(AccountMapper::toAccount);
    }

}
