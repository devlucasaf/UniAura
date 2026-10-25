package erp.uniaura.services.web.matricula.matricularAluno.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Matricula;
import erp.uniaura.persistence.entities.StatusMatricula;
import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MatricularAlunoServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarTurmaComponent buscarTurmaComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- MATRICULA O ALUNO NA TURMA APÓS VALIDAR PERÍODO E CAPACIDADE ---
    @Transactional
    public Uid matricular(Long usuarioId, MatricularAlunoParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());
        Turma turma = buscarTurmaComponent.buscarPorUid(params.getTurmaUid());

        if (!Boolean.TRUE.equals(turma.getAtiva())) {
            throw new BusinessException("Não é possível matricular alunos em uma turma inativa.");
        }

        Long ativasNoPeriodo = em()
                .createQuery("select count(m) from Matricula m where m.aluno = :aluno and m.turma.periodoLetivo = :periodoLetivo and m.status = :status", Long.class)
                .setParameter("aluno", aluno)
                .setParameter("periodoLetivo", turma.getPeriodoLetivo())
                .setParameter("status", StatusMatricula.ATIVA)
                .getSingleResult();

        if (ativasNoPeriodo > 0) {
            throw new BusinessException("O aluno já possui matrícula ATIVA no período letivo " + turma.getPeriodoLetivo() + ".");
        }

        Long ocupadas = em()
                .createQuery("select count(m) from Matricula m where m.turma = :turma and m.status = :status", Long.class)
                .setParameter("turma", turma)
                .setParameter("status", StatusMatricula.ATIVA)
                .getSingleResult();

        if (ocupadas >= turma.getCapacidadeMaxima()) {
            throw new BusinessException("A turma atingiu a capacidade máxima de " + turma.getCapacidadeMaxima() + " alunos.");
        }

        Matricula matricula = new Matricula();

        matricula.setAluno(aluno);
        matricula.setTurma(turma);
        matricula.setStatus(StatusMatricula.ATIVA);
        matricula.setObservacoes(params.getObservacoes());
        matricula.setCriadaPor(buscarUsuarioComponent.buscarPorId(usuarioId));

        if (params.getDataMatricula() == null) {
            matricula.setDataMatricula(LocalDate.now());
        } else {
            matricula.setDataMatricula(params.getDataMatricula());
        }

        matricula.persist();

        aluno.setTurmaAtualId(turma.getId());
        aluno.merge();

        return new Uid(matricula.getUid());
    }
}
