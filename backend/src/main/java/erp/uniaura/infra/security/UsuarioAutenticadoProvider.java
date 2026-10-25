package erp.uniaura.infra.security;

import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.ConsultarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioAutenticadoProvider {

    private final ConsultarUsuarioComponent consultarUsuarioComponent;

    public Optional<Usuario> obter() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            return Optional.empty();
        }

        String id = jwtAuth.getToken().getClaimAsString("user_id");
        if (id == null) {
            return Optional.empty();
        }

        return consultarUsuarioComponent.porId(Long.valueOf(id));
    }
}
