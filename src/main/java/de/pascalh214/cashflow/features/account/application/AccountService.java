package de.pascalh214.cashflow.features.account.application;

import de.pascalh214.cashflow.features.account.domain.*;
import de.pascalh214.cashflow.features.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public CreateAccountResult createBankAccount(
            UUID userId,
            String countryCode,
            int checkNumber,
            String basicBankAccountNumber
    ) {
        BankAccount bankAccount = BankAccount.addAccount(
                new AccountId(UUID.randomUUID()),
                new UserId(userId),
                new CountryCode(countryCode),
                checkNumber,
                basicBankAccountNumber
        );

        this.accountRepository.save(bankAccount);
        return new CreateAccountResult.Success(bankAccount.getId());
    }

    @Transactional
    public CreateAccountResult createCreditCard(
            UUID userId,
            String cardNumber
    ) {
        CreditCard creditCard = CreditCard.addAccount(
                new AccountId(UUID.randomUUID()),
                new UserId(userId),
                cardNumber
        );

        this.accountRepository.save(creditCard);
        return new CreateAccountResult.Success(creditCard.getId());
    }

}
