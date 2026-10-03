package erp.uniaura.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${oidc.issuer-uri}")
    private String issuerUri;

    // --- VALIDA OS TOKENS EMITIDOS PELO KEYCLOAK, USANDO O DOCUMENTO DE DESCOBERTA OIDC DO REALM ---
    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }
}
