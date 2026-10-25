package erp.uniaura.services.web.tcc.abrirTcc.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.StatusTcc;
import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AbrirTccServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarProfessorComponent buscarProfessorComponent;

    // --- ABRE O TCC DO ALUNO COM ORIENTADOR E STATUS EM ANDAMENTO ---
    @Transactional
    public Uid abrir(AbrirTccParamsV1 params) {
        Tcc tcc = new Tcc();

        tcc.setAluno(buscarAlunoComponent.buscarPorUid(params.getAlunoUid()));
        tcc.setTitulo(params.getTitulo());
        tcc.setProfessorOrientador(buscarProfessorComponent.buscarPorUid(params.getProfessorOrientadorUid()));
        tcc.setPeriodoLetivo(params.getPeriodoLetivo());
        tcc.setStatus(StatusTcc.EM_ANDAMENTO);

        if (params.getProfessorCoorientadorUid() != null) {
            tcc.setProfessorCoorientador(buscarProfessorComponent.buscarPorUid(params.getProfessorCoorientadorUid()));
        }

        tcc.persist();

        return new Uid(tcc.getUid());
    }
}
