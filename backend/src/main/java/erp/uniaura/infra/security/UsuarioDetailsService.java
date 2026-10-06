package erp.uniaura.infra.security;

import erp.uniaura.modules.usuario.model.Usuario;
import erp.uniaura.modules.usuario.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    // --- BUSCA O USUÁRIO PELO E-MAIL E RETORNA SEUS DADOS PARA O PROCESSO DE AUTENTICAÇÃO ---
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        return new UsuarioDetails(usuario);
    }
}

