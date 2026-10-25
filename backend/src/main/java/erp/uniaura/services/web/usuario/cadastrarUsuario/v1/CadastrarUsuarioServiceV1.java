package erp.uniaura.services.web.usuario.cadastrarUsuario.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.CriarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarUsuarioServiceV1 extends BaseService {

    private final CriarUsuarioComponent criarUsuarioComponent;

    @Transactional
    public Uid cadastrar(CadastrarUsuarioParamsV1 params) {
        Usuario usuario = criarUsuarioComponent.criar(params.getNome(), params.getEmail(), params.getSenha(),
                params.getCpf(), params.getTelefone(), params.getDataNascimento(), params.getAtivo(), params.getRole());

        return new Uid(usuario.getUid());
    }
}
