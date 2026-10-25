package erp.uniaura.services.web.financeiro.alterarMensalidade.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.StatusMensalidade;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.financeiro.BuscarMensalidadeComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarMensalidadeServiceV1 extends BaseService {

    private final BuscarMensalidadeComponent buscarMensalidadeComponent;
    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- MENSALIDADE JÁ PAGA É HISTÓRICO E NÃO PODE MAIS MUDAR ---
    @Transactional
    public void alterar(AlterarMensalidadeParamsV1 params) {
        Mensalidade mensalidade = buscarMensalidadeComponent.buscarPorUid(params.getUid());

        if (mensalidade.getStatus() == StatusMensalidade.PAGA) {
            throw new BusinessException("Não é possível alterar uma mensalidade já paga.");
        }

        if (!mensalidade.getAluno().getUid().equals(params.getAlunoUid())) {
            mensalidade.setAluno(buscarAlunoComponent.buscarPorUid(params.getAlunoUid()));
        }

        mensalidade.setCompetencia(params.getCompetencia());
        mensalidade.setValor(params.getValor());
        mensalidade.setVencimento(params.getVencimento());
        mensalidade.setDescricao(params.getDescricao());

        if (params.getStatus() != null) {
            mensalidade.setStatus(params.getStatus());
        }

        mensalidade.merge();
    }
}
