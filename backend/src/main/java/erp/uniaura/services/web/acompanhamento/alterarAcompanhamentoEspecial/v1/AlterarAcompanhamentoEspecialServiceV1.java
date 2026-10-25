package erp.uniaura.services.web.acompanhamento.alterarAcompanhamentoEspecial.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.AcompanhamentoEspecial;
import erp.uniaura.persistence.entities.StatusAcompanhamento;
import erp.uniaura.services.common.acompanhamento.BuscarAcompanhamentoEspecialComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AlterarAcompanhamentoEspecialServiceV1 extends BaseService {

    private final BuscarAcompanhamentoEspecialComponent buscarAcompanhamentoEspecialComponent;

    // --- ATUALIZA OS DADOS E O STATUS DE UM ACOMPANHAMENTO AINDA EM ANDAMENTO ---
    @Transactional
    public void alterar(AlterarAcompanhamentoEspecialParamsV1 params) {
        AcompanhamentoEspecial acompanhamento = buscarAcompanhamentoEspecialComponent.buscarPorUid(params.getUid());

        if (acompanhamento.getStatus() == StatusAcompanhamento.ENCERRADO) {
            throw new BusinessException("Acompanhamento encerrado não pode ser alterado.");
        }

        acompanhamento.setTipoNecessidade(params.getTipoNecessidade());
        acompanhamento.setDescricaoNecessidade(params.getDescricaoNecessidade());
        acompanhamento.setRecursosNecessarios(params.getRecursosNecessarios());
        acompanhamento.setPossuiLaudo(params.getPossuiLaudo());
        acompanhamento.setStatus(params.getStatus());

        if (params.getStatus() == StatusAcompanhamento.ENCERRADO) {
            acompanhamento.setDataEncerramento(LocalDate.now());
        }

        acompanhamento.merge();
    }
}
