package erp.uniaura.infra.security;

import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.ConsultarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final ConsultarUsuarioComponent consultarUsuarioComponent;

    // --- BUSCA O USUÁRIO PELO E-MAIL E RETORNA SEUS DADOS PARA O PROCESSO DE AUTENTICAÇÃO ---
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = consultarUsuarioComponent.porEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        return new UsuarioDetails(usuario);
    }
}

