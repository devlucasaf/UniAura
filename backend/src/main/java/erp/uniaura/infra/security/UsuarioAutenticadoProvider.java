package erp.uniaura.infra.security;

import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;
import erp.uniaura.modules.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioAutenticadoProvider {

    private final UsuarioRepository usuarioRepository;

    // --- RESOLVE O USUÁRIO AUTENTICADO, PROVISIONANDO O PERFIL LOCAL NO PRIMEIRO LOGIN VIA KEYCLOAK ---
    @Transactional
    public Optional<Usuario> obter() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            return Optional.empty();
        }

        Jwt token = jwtAuth.getToken();
        String keycloakSub = token.getSubject();

        return usuarioRepository.findByKeycloakSub(keycloakSub)
                .or(() -> vincularPorEmail(token, keycloakSub))
                .or(() -> provisionar(token, keycloakSub));
    }

    // --- VINCULA UM PERFIL JÁ EXISTENTE (CRIADO ANTES DA MIGRAÇÃO PARA O KEYCLOAK) PELO E-MAIL ---
    private Optional<Usuario> vincularPorEmail(Jwt token, String keycloakSub) {
        String email = token.getClaimAsString("email");
        if (email == null) {
            return Optional.empty();
        }
        return usuarioRepository.findByEmail(email).map(usuario -> {
            usuario.setKeycloakSub(keycloakSub);
            return usuarioRepository.save(usuario);
        });
    }

    // --- CRIA O PERFIL LOCAL NO PRIMEIRO LOGIN, A PARTIR DOS DADOS DO PRÓPRIO TOKEN ---
    private Optional<Usuario> provisionar(Jwt token, String keycloakSub) {
        TipoUsuario tipoUsuarioRole = extrairRole(token);
        if (tipoUsuarioRole == null) {
            return Optional.empty();
        }

        Usuario usuario = Usuario.builder()
                .nome(nomeDoToken(token))
                .email(token.getClaimAsString("email"))
                .keycloakSub(keycloakSub)
                .ativo(true)
                .role(tipoUsuarioRole)
                .build();

        return Optional.of(usuarioRepository.save(usuario));
    }

    // --- EXTRAI A PRIMEIRA ROLE DA CLAIM "roles" QUE CORRESPONDA A UM TipoUsuario CONHECIDO ---
    private TipoUsuario extrairRole(Jwt token) {
        List<String> roles = token.getClaimAsStringList("roles");
        if (roles == null) {
            return null;
        }

        for (String role : roles) {
            try {
                return TipoUsuario.valueOf(role);
            } catch (IllegalArgumentException ignorada) {
                // --- CLAIM COM UM VALOR QUE NÃO CORRESPONDE A NENHUM TipoUsuario CONHECIDO ---
            }
        }
        return null;
    }

    // --- MONTA O NOME A PARTIR DAS CLAIMS PADRÃO DO OIDC ---
    private String nomeDoToken(Jwt token) {
        String nome = token.getClaimAsString("name");
        if (nome != null && !nome.isBlank()) {
            return nome;
        }
        return token.getClaimAsString("preferred_username");
    }
}
