package de.pascalh214.cashflow;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Value("${springdoc.oauth2.auth-url}")
    private String authorizationUrl;

    @Value("${springdoc.oauth2.token-url}")
    private String tokenUrl;

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme keycloakScheme = new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows().authorizationCode(new OAuthFlow()
                        .authorizationUrl(authorizationUrl)
                        .tokenUrl(tokenUrl)
                        .scopes(new Scopes()
                                .addString("openid", "OpenID Connect scope")
                                .addString("profile", "User profile scope")
                                .addString("email", "User email scope"))));

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("keycloak", keycloakScheme))
                .addSecurityItem(new SecurityRequirement().addList("keycloak"));
    }
}
