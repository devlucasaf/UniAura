package erp.uniaura.services.web.responsavel.vincularAlunoAoResponsavel.v1;

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
public class VincularAlunoAoResponsavelServiceV1 extends BaseService {

    private final BuscarResponsavelComponent buscarResponsavelComponent;
    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarVinculoComponent buscarVinculoComponent;

    // --- VINCULA O ALUNO AO RESPONSÁVEL, VALIDANDO DUPLICIDADE ---
    @Transactional
    public void vincular(VincularAlunoAoResponsavelParamsV1 params) {
        Responsavel responsavel = buscarResponsavelComponent.buscarPorUid(params.getResponsavelUid());
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());

        if (buscarVinculoComponent.buscar(responsavel, aluno).isPresent()) {
            throw new BusinessException("Este aluno já está vinculado ao responsável.");
        }

        ResponsavelAluno vinculo = new ResponsavelAluno();

        vinculo.setResponsavel(responsavel);
        vinculo.setAluno(aluno);
        vinculo.setObservacao(params.getObservacao());
        vinculo.persist();
    }
}
