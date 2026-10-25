package erp.uniaura.services.web.usuario.alterarUsuario.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;
import erp.uniaura.services.common.usuario.ConsultarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarUsuarioServiceV1 extends BaseService {

    private final BuscarUsuarioComponent    buscarUsuarioComponent;
    private final ConsultarUsuarioComponent consultarUsuarioComponent;
    private final PasswordEncoder           passwordEncoder;

    @Transactional
    public void alterar(AlterarUsuarioParamsV1 params) {
        Usuario usuario = buscarUsuarioComponent.buscarPorUid(params.getUid());

        boolean trocouEmail = !usuario.getEmail().equalsIgnoreCase(params.getEmail());

        if (trocouEmail && consultarUsuarioComponent.porEmail(params.getEmail()).isPresent()) {
            throw new BusinessException("Já existe um usuário cadastrado com o e-mail: " + params.getEmail());
        }

        usuario.setNome(params.getNome());
        usuario.setEmail(params.getEmail());
        usuario.setCpf(params.getCpf());
        usuario.setTelefone(params.getTelefone());
        usuario.setDataNascimento(params.getDataNascimento());
        usuario.setRole(params.getRole());

        if (params.getAtivo() != null) {
            usuario.setAtivo(params.getAtivo());
        }

        if (params.getSenha() != null && !params.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(params.getSenha()));
        }

        usuario.merge();
    }
}
