package erp.uniaura.services.web.responsavel.cadastrarResponsavel.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.Uid;

import erp.uniaura.services.common.usuario.CriarUsuarioComponent;
import erp.uniaura.persistence.entities.Responsavel;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarResponsavelServiceV1 extends BaseService {
    private final CriarUsuarioComponent criarUsuarioComponent;

    // --- CRIA O USUÁRIO E O RESPONSÁVEL VINCULADO ---
    @Transactional
    public Uid cadastrar(CadastrarResponsavelParamsV1 params) {
        Usuario usuario = criarUsuarioComponent.criar(
                params.getNome(),
                params.getEmail(),
                params.getSenha(),
                params.getCpf(),
                params.getTelefone(),
                params.getDataNascimento(),
                true,
                TipoUsuario.RESPONSAVEL
        );

        Responsavel responsavel = new Responsavel();

        responsavel.setUsuario(usuario);
        responsavel.setParentesco(params.getParentesco());
        responsavel.persist();

        return new Uid(responsavel.getUid());
    }
}
