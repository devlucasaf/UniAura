package erp.uniaura.services.web.estagio.encerrarEstagio.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Estagio;
import erp.uniaura.persistence.entities.StatusEstagio;
import erp.uniaura.services.common.estagio.BuscarEstagioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EncerrarEstagioServiceV1 extends BaseService {

    private final BuscarEstagioComponent buscarEstagioComponent;

    // --- ENCERRA O ESTÁGIO COM O STATUS INFORMADO E GRAVA A DATA FIM ---
    @Transactional
    public void encerrar(EncerrarEstagioParamsV1 params) {
        Estagio estagio = buscarEstagioComponent.buscarPorUid(params.getUid());

        if (params.getStatus() == StatusEstagio.EM_ANDAMENTO) {
            throw new BusinessException("O status de encerramento não pode ser EM_ANDAMENTO.");
        }

        if (estagio.getStatus() == StatusEstagio.CONCLUIDO || estagio.getStatus() == StatusEstagio.CANCELADO) {
            throw new BusinessException("Este estágio já está encerrado.");
        }

        estagio.setStatus(params.getStatus());
        estagio.setDataFimEfetiva(params.getDataFimEfetiva());
        estagio.merge();
    }
}
