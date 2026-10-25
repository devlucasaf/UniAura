package erp.uniaura.services.web.acompanhamento.detalharAcompanhamentoEspecial.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.AcompanhamentoEspecial;
import erp.uniaura.services.common.acompanhamento.BuscarAcompanhamentoEspecialComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharAcompanhamentoEspecialServiceV1 extends BaseService {

    private final BuscarAcompanhamentoEspecialComponent buscarAcompanhamentoEspecialComponent;

    // --- DEVOLVE OS DADOS COMPLETOS DE UM ACOMPANHAMENTO, INCLUINDO A DESCRIÇÃO DA NECESSIDADE ---
    @Transactional(readOnly = true)
    public DetalharAcompanhamentoEspecialRetornoV1 detalhar(DetalharAcompanhamentoEspecialParamsV1 params) {
        AcompanhamentoEspecial acompanhamento = buscarAcompanhamentoEspecialComponent.buscarPorUid(params.getUid());

        DetalharAcompanhamentoEspecialRetornoV1 retorno = new DetalharAcompanhamentoEspecialRetornoV1();

        retorno.setUid(acompanhamento.getUid());
        retorno.setAlunoUid(acompanhamento.getAluno().getUid());
        retorno.setAlunoNome(acompanhamento.getAluno().getUsuario().getNome());
        retorno.setMatriculaRA(acompanhamento.getAluno().getMatriculaRA());
        retorno.setTipoNecessidade(acompanhamento.getTipoNecessidade());
        retorno.setDescricaoNecessidade(acompanhamento.getDescricaoNecessidade());
        retorno.setRecursosNecessarios(acompanhamento.getRecursosNecessarios());
        retorno.setPossuiLaudo(acompanhamento.getPossuiLaudo());
        retorno.setStatus(acompanhamento.getStatus());
        retorno.setDataInicio(acompanhamento.getDataInicio());
        retorno.setDataEncerramento(acompanhamento.getDataEncerramento());

        if (acompanhamento.getResponsavelPeloAcompanhamento() != null) {
            retorno.setResponsavelPeloAcompanhamento(acompanhamento.getResponsavelPeloAcompanhamento().getNome());
        }

        retorno.setCriadoEm(acompanhamento.getCriadoEm());
        retorno.setAtualizadoEm(acompanhamento.getAtualizadoEm());

        return retorno;
    }
}
