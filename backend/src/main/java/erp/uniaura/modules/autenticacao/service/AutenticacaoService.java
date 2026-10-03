package erp.uniaura.modules.autenticacao.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.usuario.dto.UsuarioResponseDTO;
import erp.uniaura.modules.usuario.model.Usuario;
import erp.uniaura.modules.usuario.service.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;
    private final UsuarioService usuarioService;

    // --- RETORNA OS DADOS DO USUÁRIO AUTENTICADO (LOGIN É FEITO VIA KEYCLOAK) ---
    @Transactional
    public UsuarioResponseDTO dadosDoUsuario() {
        Usuario usuario = usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
        return usuarioService.buscarPorId(usuario.getId());
    }
}
