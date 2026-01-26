package de.pascalh214.cashflow.features.account.api;

import de.pascalh214.cashflow.features.account.api.dtos.CreateAccountResponse;
import de.pascalh214.cashflow.features.account.api.dtos.CreateBankAccountRequest;
import de.pascalh214.cashflow.features.account.api.dtos.CreateCreditCardAccountRequest;
import de.pascalh214.cashflow.features.account.application.AccountService;
import de.pascalh214.cashflow.features.account.application.CreateAccountResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/bank-account")
    public ResponseEntity<?> createBankAccountAccount(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateBankAccountRequest request
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());

        CreateAccountResult createAccountResult = accountService.createBankAccount(
                userId,
                request.countryCode(),
                request.checkNumber(),
                request.basicBankAccountNumber()
        );
        if (createAccountResult instanceof CreateAccountResult.Success(
                de.pascalh214.cashflow.features.account.domain.AccountId id
        )) {
            return ResponseEntity.ok(new CreateAccountResponse(id));
        }

        return ResponseEntity.status(400)
                .body((CreateAccountResult.Failure) (createAccountResult));
    }

    @PostMapping("/credit-card")
    public ResponseEntity<?> createCreditCardAccount(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateCreditCardAccountRequest request
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());

        CreateAccountResult createAccountResult = accountService.createCreditCard(
                userId,
                request.cardNumber()
        );
        if (createAccountResult instanceof CreateAccountResult.Success(
                de.pascalh214.cashflow.features.account.domain.AccountId id
        )) {
            return ResponseEntity.ok(new CreateAccountResponse(id));
        }

        return ResponseEntity.status(400)
                .body((CreateAccountResult.Failure) (createAccountResult));
    }

}
