package de.pascalh214.cashflow;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public abstract class KeycloakTestSupport {

    private static final KeycloakContainer KEYCLOAK =
            new KeycloakContainer().withRealmImportFile("keycloak/realm-export.json");

    @BeforeAll
    static void startKeycloak() {
        KEYCLOAK.start();
    }

    @AfterAll
    static void stopKeycloak() {
        KEYCLOAK.stop();
    }

    @DynamicPropertySource
    static void registerKeycloakProps(DynamicPropertyRegistry registry) {
        String issuer = KEYCLOAK.getAuthServerUrl() + "realms/cashflow";
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                () -> issuer + "/protocol/openid-connect/certs");
        registry.add("springdoc.oauth2.auth-url",
                () -> issuer + "/protocol/openid-connect/auth");
        registry.add("springdoc.oauth2.token-url",
                () -> issuer + "/protocol/openid-connect/token");
    }
}
