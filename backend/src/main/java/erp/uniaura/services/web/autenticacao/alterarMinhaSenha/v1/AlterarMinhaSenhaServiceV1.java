package erp.uniaura.services.web.autenticacao.alterarMinhaSenha.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarMinhaSenhaServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final PasswordEncoder passwordEncoder;

    // --- ALTERA A SENHA DO USUÁRIO LOGADO APÓS VALIDAR A SENHA ATUAL ---
    @Transactional
    public void alterar(Long usuarioId, AlterarMinhaSenhaParamsV1 params) {
        Usuario usuario = buscarUsuarioComponent.buscarPorId(usuarioId);

        if (!passwordEncoder.matches(params.getSenhaAtual(), usuario.getSenha())) {
            throw new BusinessException("A senha atual está incorreta.");
        }

        if (passwordEncoder.matches(params.getNovaSenha(), usuario.getSenha())) {
            throw new BusinessException("A nova senha deve ser diferente da senha atual.");
        }

        usuario.setSenha(passwordEncoder.encode(params.getNovaSenha()));
        usuario.merge();
    }
}
