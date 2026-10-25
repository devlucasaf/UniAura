package erp.uniaura.services.web.responsavel.desvincularAlunoDoResponsavel.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Responsavel;
import erp.uniaura.persistence.entities.ResponsavelAluno;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.responsavel.BuscarResponsavelComponent;
import erp.uniaura.services.common.responsavel.BuscarVinculoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DesvincularAlunoDoResponsavelServiceV1 extends BaseService {

    private final BuscarResponsavelComponent buscarResponsavelComponent;
    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarVinculoComponent buscarVinculoComponent;

    // --- DESVINCULA O ALUNO DO RESPONSÁVEL ---
    @Transactional
    public void desvincular(DesvincularAlunoDoResponsavelParamsV1 params) {
        Responsavel responsavel = buscarResponsavelComponent.buscarPorUid(params.getResponsavelUid());
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());

        ResponsavelAluno vinculo = buscarVinculoComponent.buscar(responsavel, aluno)
                .orElseThrow(() -> new BusinessException("Este aluno não está vinculado ao responsável."));

        vinculo.remove();
    }
}
