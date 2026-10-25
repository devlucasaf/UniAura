package erp.uniaura.services.web.responsavel.detalharResponsavel.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Responsavel;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.responsavel.BuscarResponsavelComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharResponsavelServiceV1 extends BaseService {

    private final BuscarResponsavelComponent buscarResponsavelComponent;

    // --- DETALHA O RESPONSÁVEL E SEUS DADOS DE USUÁRIO ---
    @Transactional(readOnly = true)
    public DetalharResponsavelRetornoV1 detalhar(DetalharResponsavelParamsV1 params) {
        Responsavel responsavel = buscarResponsavelComponent.buscarPorUid(params.getUid());
        Usuario usuario = responsavel.getUsuario();

        DetalharResponsavelRetornoV1 retorno = new DetalharResponsavelRetornoV1();

        retorno.setUid(responsavel.getUid());
        retorno.setNome(usuario.getNome());
        retorno.setEmail(usuario.getEmail());
        retorno.setCpf(usuario.getCpf());
        retorno.setTelefone(usuario.getTelefone());
        retorno.setDataNascimento(usuario.getDataNascimento());
        retorno.setParentesco(responsavel.getParentesco());
        retorno.setCriadoEm(responsavel.getCriadoEm());
        retorno.setAtualizadoEm(responsavel.getAtualizadoEm());

        return retorno;
    }
}
