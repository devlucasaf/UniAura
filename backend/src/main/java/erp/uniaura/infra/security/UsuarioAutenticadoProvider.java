package erp.uniaura.infra.security;

import erp.uniaura.modules.usuario.model.Usuario;
import erp.uniaura.modules.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioAutenticadoProvider {

    private final UsuarioRepository usuarioRepository;

    public Optional<Usuario> obter() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            return Optional.empty();
        }

        String id = jwtAuth.getToken().getClaimAsString("user_id");
        if (id == null) {
            return Optional.empty();
        }

        return usuarioRepository.findById(Long.valueOf(id));
    }
}
