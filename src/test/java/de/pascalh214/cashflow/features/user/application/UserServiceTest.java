package de.pascalh214.cashflow.features.user.application;

import de.pascalh214.cashflow.features.account.domain.AccountId;
import de.pascalh214.cashflow.features.account.domain.BankAccount;
import de.pascalh214.cashflow.features.country.domain.Country;
import de.pascalh214.cashflow.features.country.domain.CountryId;
import de.pascalh214.cashflow.features.account.domain.CreditCard;
import de.pascalh214.cashflow.features.user.domain.User;
import de.pascalh214.cashflow.features.user.domain.UserId;
import de.pascalh214.cashflow.features.user.domain.TestUsers;
import de.pascalh214.cashflow.features.user.exceptions.IdNotValidException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUserInformationThrowsOnInvalidUuid() {
        assertThatThrownBy(() -> userService.getUserInformation("not-a-uuid"))
                .isInstanceOf(IdNotValidException.class)
                .hasMessage("Invalid user id");
    }

    @Test
    void getUserInformationCreatesUserWhenMissing() {
        UUID rawId = UUID.randomUUID();
        UserId userId = new UserId(rawId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserInformationResult result = userService.getUserInformation(rawId.toString());

        assertThat(result).isInstanceOf(UserInformationResult.Success.class);
        UserInformationResult.Success success = (UserInformationResult.Success) result;
        assertThat(success.id()).isEqualTo(userId);
        assertThat(success.accounts()).isEmpty();
        verify(userRepository).save(any(User.class));
    }

    @Test
    void getUserInformationMapsAccountSummaries() {
        UUID rawId = UUID.randomUUID();
        UserId userId = new UserId(rawId);
        BankAccount bankAccount = BankAccount.addAccount(
                new AccountId(UUID.randomUUID()),
                userId,
                Country.rehydrate(new CountryId("DE"), "Germany", List.of(), List.of()),
                12,
                "1234567890",
                "DE121234567890"
        );
        CreditCard creditCard = CreditCard.addAccount(
                new AccountId(UUID.randomUUID()),
                userId,
                "4111111111111111"
        );
        User user = TestUsers.rehydrateWithAccounts(
                userId,
                Instant.now(),
                Instant.now(),
                List.of(bankAccount, creditCard)
        );
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserInformationResult result = userService.getUserInformation(rawId.toString());

        assertThat(result).isInstanceOf(UserInformationResult.Success.class);
        UserInformationResult.Success success = (UserInformationResult.Success) result;
        assertThat(success.accounts()).hasSize(2);
        assertThat(success.accounts()).anySatisfy(account -> {
            assertThat(account.type()).isEqualTo(bankAccount.getType());
            assertThat(account.countryCode()).isEqualTo("DE");
            assertThat(account.checkDigits()).isEqualTo(12);
            assertThat(account.basicBankAccountNumber()).isEqualTo("1234567890");
            assertThat(account.iban()).isEqualTo("DE121234567890");
            assertThat(account.cardNumber()).isNull();
        });
        assertThat(success.accounts()).anySatisfy(account -> {
            assertThat(account.type()).isEqualTo(creditCard.getType());
            assertThat(account.cardNumber()).isEqualTo("4111111111111111");
            assertThat(account.countryCode()).isNull();
            assertThat(account.checkDigits()).isNull();
            assertThat(account.basicBankAccountNumber()).isNull();
        });
    }
}
