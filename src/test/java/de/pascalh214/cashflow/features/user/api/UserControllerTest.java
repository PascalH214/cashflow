package de.pascalh214.cashflow.features.user.api;

import de.pascalh214.cashflow.features.user.api.dtos.UserInformationResponse;
import de.pascalh214.cashflow.features.user.application.UserInformationResult;
import de.pascalh214.cashflow.features.user.application.UserService;
import de.pascalh214.cashflow.features.user.domain.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void returnsNotFoundOnFailure() {
        when(userService.getUserInformation("subject"))
                .thenReturn(new UserInformationResult.Failure("not found"));

        ResponseEntity<UserInformationResponse> response = userController.index(jwtWithSubject("subject"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void returnsUserInformationOnSuccess() {
        UserId userId = new UserId(UUID.randomUUID());
        UserInformationResult.Success success = new UserInformationResult.Success(
                userId,
                List.of()
        );
        when(userService.getUserInformation("subject")).thenReturn(success);

        ResponseEntity<UserInformationResponse> response = userController.index(jwtWithSubject("subject"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(userId);
        assertThat(response.getBody().accounts()).isEmpty();
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
