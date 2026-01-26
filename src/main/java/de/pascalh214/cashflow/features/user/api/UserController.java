package de.pascalh214.cashflow.features.user.api;

import de.pascalh214.cashflow.features.user.application.UserInformationResult;
import de.pascalh214.cashflow.features.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserInformationResult> index(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(this.userService.getUserInformation(jwt.getSubject()));
    }

}
