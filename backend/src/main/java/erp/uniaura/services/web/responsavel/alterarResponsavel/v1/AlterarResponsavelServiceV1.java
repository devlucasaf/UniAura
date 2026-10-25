package erp.uniaura.services.web.responsavel.alterarResponsavel.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Responsavel;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.responsavel.BuscarResponsavelComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarResponsavelServiceV1 extends BaseService {

    private final BuscarResponsavelComponent buscarResponsavelComponent;

    // --- ALTERA OS DADOS DO RESPONSÁVEL E DO USUÁRIO VINCULADO ---
    @Transactional
    public void alterar(AlterarResponsavelParamsV1 params) {
        Responsavel responsavel = buscarResponsavelComponent.buscarPorUid(params.getUid());
        Usuario usuario = responsavel.getUsuario();

        usuario.setNome(params.getNome());
        usuario.setEmail(params.getEmail());
        usuario.setCpf(params.getCpf());
        usuario.setTelefone(params.getTelefone());
        usuario.setDataNascimento(params.getDataNascimento());
        usuario.merge();

        responsavel.setParentesco(params.getParentesco());
        responsavel.merge();
    }
}
