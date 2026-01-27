package de.pascalh214.cashflow.features.account.application;

import de.pascalh214.cashflow.features.account.domain.*;
import de.pascalh214.cashflow.features.country.application.CountryRepository;
import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.country.domain.CountryId;
import de.pascalh214.cashflow.features.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CountryRepository countryRepository;

    @Transactional
    public CreateAccountResult createBankAccount(
            UUID userId,
            String countryCode,
            int checkNumber,
            String basicBankAccountNumber
    ) {
        CountryId countryId = new CountryId(countryCode.toUpperCase());
        Country country = countryRepository.findById(countryId)
                .orElse(null);
        if (country == null) {
            return new CreateAccountResult.Failure("Country not found");
        }
        String iban = buildIban(country.getId().value(), checkNumber, basicBankAccountNumber);

        BankAccount bankAccount = BankAccount.addAccount(
                new AccountId(UUID.randomUUID()),
                new UserId(userId),
                country,
                checkNumber,
                basicBankAccountNumber,
                iban
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

    private String buildIban(String countryCode, int checkNumber, String basicBankAccountNumber) {
        String checkDigits = String.format("%02d", checkNumber);
        return countryCode + checkDigits + basicBankAccountNumber;
    }

}
