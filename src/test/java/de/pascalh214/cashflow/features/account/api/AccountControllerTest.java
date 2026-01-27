package de.pascalh214.cashflow.features.account.api;

import de.pascalh214.cashflow.features.account.api.dtos.CreateAccountResponse;
import de.pascalh214.cashflow.features.account.api.dtos.CreateBankAccountRequest;
import de.pascalh214.cashflow.features.account.api.dtos.CreateCreditCardAccountRequest;
import de.pascalh214.cashflow.features.account.application.AccountService;
import de.pascalh214.cashflow.features.account.application.CreateAccountResult;
import de.pascalh214.cashflow.features.account.domain.AccountId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    void createBankAccountReturnsOkOnSuccess() {
        AccountId accountId = new AccountId(UUID.randomUUID());
        when(accountService.createBankAccount(
                UUID.fromString("1a1b1c1d-2e2f-3a3b-4c4d-5e5f6a6b7c7d"),
                "DE",
                12,
                "1234567890"
        )).thenReturn(new CreateAccountResult.Success(accountId));

        ResponseEntity<?> response = accountController.createBankAccountAccount(
                jwtWithSubject("1a1b1c1d-2e2f-3a3b-4c4d-5e5f6a6b7c7d"),
                new CreateBankAccountRequest("DE", 12, "1234567890")
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(CreateAccountResponse.class);
        assertThat(((CreateAccountResponse) response.getBody()).id()).isEqualTo(accountId);
    }

    @Test
    void createBankAccountReturnsBadRequestOnFailure() {
        CreateAccountResult.Failure failure = new CreateAccountResult.Failure("invalid");
        when(accountService.createBankAccount(
                UUID.fromString("1a1b1c1d-2e2f-3a3b-4c4d-5e5f6a6b7c7d"),
                "DE",
                12,
                "1234567890"
        )).thenReturn(failure);

        ResponseEntity<?> response = accountController.createBankAccountAccount(
                jwtWithSubject("1a1b1c1d-2e2f-3a3b-4c4d-5e5f6a6b7c7d"),
                new CreateBankAccountRequest("DE", 12, "1234567890")
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(failure);
    }

    @Test
    void createCreditCardReturnsOkOnSuccess() {
        AccountId accountId = new AccountId(UUID.randomUUID());
        when(accountService.createCreditCard(
                UUID.fromString("1a1b1c1d-2e2f-3a3b-4c4d-5e5f6a6b7c7d"),
                "4111111111111111"
        )).thenReturn(new CreateAccountResult.Success(accountId));

        ResponseEntity<?> response = accountController.createCreditCardAccount(
                jwtWithSubject("1a1b1c1d-2e2f-3a3b-4c4d-5e5f6a6b7c7d"),
                new CreateCreditCardAccountRequest("4111111111111111")
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(CreateAccountResponse.class);
        assertThat(((CreateAccountResponse) response.getBody()).id()).isEqualTo(accountId);
    }

    @Test
    void createCreditCardReturnsBadRequestOnFailure() {
        CreateAccountResult.Failure failure = new CreateAccountResult.Failure("invalid");
        when(accountService.createCreditCard(
                UUID.fromString("1a1b1c1d-2e2f-3a3b-4c4d-5e5f6a6b7c7d"),
                "4111111111111111"
        )).thenReturn(failure);

        ResponseEntity<?> response = accountController.createCreditCardAccount(
                jwtWithSubject("1a1b1c1d-2e2f-3a3b-4c4d-5e5f6a6b7c7d"),
                new CreateCreditCardAccountRequest("4111111111111111")
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo(failure);
    }

    @Test
    void createBankAccountThrowsOnInvalidJwtSubject() {
        assertThatThrownBy(() -> accountController.createBankAccountAccount(
                jwtWithSubject("invalid"),
                new CreateBankAccountRequest("DE", 12, "1234567890")
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createCreditCardThrowsOnInvalidJwtSubject() {
        assertThatThrownBy(() -> accountController.createCreditCardAccount(
                jwtWithSubject("invalid"),
                new CreateCreditCardAccountRequest("4111111111111111")
        )).isInstanceOf(IllegalArgumentException.class);
    }

    private Jwt jwtWithSubject(String subject) {
        return Jwt.withTokenValue("token")
                .header("alg", "none")
                .subject(subject)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60))
                .build();
    }
}
