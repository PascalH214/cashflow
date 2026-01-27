package de.pascalh214.cashflow.features.user.application;

import de.pascalh214.cashflow.features.account.domain.Account;
import de.pascalh214.cashflow.features.account.domain.BankAccount;
import de.pascalh214.cashflow.features.account.domain.CreditCard;
import de.pascalh214.cashflow.features.user.domain.User;
import de.pascalh214.cashflow.features.user.domain.UserId;
import de.pascalh214.cashflow.features.user.exceptions.IdNotValidException;
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
    public User getUser(String userId) {
        UserId id;
        try {
            id = new UserId(UUID.fromString(userId));
        } catch (IllegalArgumentException e) {
            throw new IdNotValidException("Invalid user id");
        }

        return this.userRepository.findById(id).orElse(
                this.userRepository.save(User.createUser(Instant.now(), id))
        );
    }

    @Transactional
    public UserInformationResult getUserInformation(String userId) {
        User user = this.getUser(userId);

        List<UserInformationResult.AccountSummary> accounts = user.getAccounts().stream()
                .map(this::toAccountSummary)
                .toList();

        return new UserInformationResult.Success(user.getId(), accounts);
    }

    private UserInformationResult.AccountSummary toAccountSummary(Account account) {
        if (account instanceof BankAccount bankAccount) {
            return new UserInformationResult.AccountSummary(
                    bankAccount.getId(),
                    bankAccount.getType(),
                    bankAccount.getCountry().getId().value(),
                    bankAccount.getCheckDigits(),
                    bankAccount.getBasicBankAccountNumber(),
                    bankAccount.getIban(),
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
                null,
                creditCard.getCardNumber()
        );
    }

}
