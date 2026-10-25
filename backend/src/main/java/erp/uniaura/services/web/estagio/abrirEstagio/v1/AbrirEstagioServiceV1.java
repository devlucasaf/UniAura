package erp.uniaura.services.web.estagio.abrirEstagio.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Estagio;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.StatusEstagio;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AbrirEstagioServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarProfessorComponent buscarProfessorComponent;

    // --- ABRE O ESTÁGIO DO ALUNO COM STATUS EM ANDAMENTO ---
    @Transactional
    public Uid abrir(AbrirEstagioParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());

        if (params.getDataFimPrevista().isBefore(params.getDataInicio())) {
            throw new BusinessException("A data de fim prevista não pode ser anterior à data de início.");
        }

        Estagio estagio = new Estagio();

        estagio.setAluno(aluno);
        estagio.setEmpresaConcedente(params.getEmpresaConcedente());
        estagio.setSupervisorEmpresa(params.getSupervisorEmpresa());
        estagio.setDataInicio(params.getDataInicio());
        estagio.setDataFimPrevista(params.getDataFimPrevista());
        estagio.setCargaHorariaTotal(params.getCargaHorariaTotal());
        estagio.setCargaHorariaCumprida(0);
        estagio.setStatus(StatusEstagio.EM_ANDAMENTO);

        if (params.getProfessorOrientadorUid() != null) {
            Professor orientador = buscarProfessorComponent.buscarPorUid(params.getProfessorOrientadorUid());
            estagio.setProfessorOrientador(orientador);
        }

        estagio.persist();

        return new Uid(estagio.getUid());
    }
}
