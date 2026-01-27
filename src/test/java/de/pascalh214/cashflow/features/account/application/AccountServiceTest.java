package de.pascalh214.cashflow.features.account.application;

import de.pascalh214.cashflow.features.account.domain.Account;
import de.pascalh214.cashflow.features.account.domain.AccountType;
import de.pascalh214.cashflow.features.account.domain.BankAccount;
import de.pascalh214.cashflow.features.account.domain.CreditCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createBankAccountPersistsBankAccount() {
        UUID userId = UUID.randomUUID();

        CreateAccountResult result = accountService.createBankAccount(
                userId,
                "DE",
                12,
                "1234567890"
        );

        assertThat(result).isInstanceOf(CreateAccountResult.Success.class);
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());
        assertThat(accountCaptor.getValue()).isInstanceOf(BankAccount.class);
        assertThat(accountCaptor.getValue().getType()).isEqualTo(AccountType.BANK);
    }

    @Test
    void createCreditCardPersistsCreditCard() {
        UUID userId = UUID.randomUUID();

        CreateAccountResult result = accountService.createCreditCard(
                userId,
                "4111111111111111"
        );

        assertThat(result).isInstanceOf(CreateAccountResult.Success.class);
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());
        assertThat(accountCaptor.getValue()).isInstanceOf(CreditCard.class);
        assertThat(accountCaptor.getValue().getType()).isEqualTo(AccountType.CREDIT_CARD);
    }
}
