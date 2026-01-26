package de.pascalh214.cashflow.features.user.application;

import de.pascalh214.cashflow.features.account.domain.Account;
import de.pascalh214.cashflow.features.account.domain.BankAccount;
import de.pascalh214.cashflow.features.account.domain.CreditCard;
import de.pascalh214.cashflow.features.user.domain.User;
import de.pascalh214.cashflow.features.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void ensureUserExists(String userId) {
        UserId id;
        try {
            id = new UserId(UUID.fromString(userId));
        } catch (IllegalArgumentException e) {
            throw e;
        }

        Optional<User> foundUser = this.userRepository.findById(id);
        if (foundUser.isEmpty()) {
            this.userRepository.save(User.createUser(Instant.now(), id));
        }
    }

    @Transactional
    public UserInformationResult getUserInformation(String userId) {
        UserId id;
        try {
            id = new UserId(UUID.fromString(userId));
        } catch (IllegalArgumentException e) {
            return new UserInformationResult.Failure("Invalid user id");
        }

        Optional<User> foundUser = this.userRepository.findById(id);
        User user = foundUser.orElseGet(()
                -> this.userRepository.save(User.createUser(Instant.now(), id)));

        List<UserInformationResult.AccountSummary> accounts = user.getAccounts().stream()
                .map(this::toAccountSummary)
                .toList();
        return new UserInformationResult.Success(id, accounts);
    }

    private UserInformationResult.AccountSummary toAccountSummary(Account account) {
        if (account instanceof BankAccount bankAccount) {
            return new UserInformationResult.AccountSummary(
                    bankAccount.getId(),
                    bankAccount.getType(),
                    bankAccount.getCountryCode().value(),
                    bankAccount.getCheckDigits(),
                    bankAccount.getBasicBankAccountNumber(),
                    null
            );
        }

        CreditCard creditCard = (CreditCard) account;
        return new UserInformationResult.AccountSummary(
                creditCard.getId(),
                creditCard.getType(),
                null,
                null,
                null,
                creditCard.getCardNumber()
        );
    }

}
