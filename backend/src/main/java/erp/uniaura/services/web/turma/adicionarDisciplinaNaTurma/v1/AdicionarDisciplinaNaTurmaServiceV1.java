package erp.uniaura.services.web.turma.adicionarDisciplinaNaTurma.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.services.common.disciplina.BuscarDisciplinaComponent;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;
import erp.uniaura.services.common.professor.VerificarProfessorHabilitadoComponent;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdicionarDisciplinaNaTurmaServiceV1 extends BaseService {

    private final BuscarTurmaComponent buscarTurmaComponent;
    private final BuscarDisciplinaComponent buscarDisciplinaComponent;
    private final BuscarProfessorComponent buscarProfessorComponent;
    private final VerificarProfessorHabilitadoComponent verificarProfessorHabilitadoComponent;

    @Transactional
    public Uid adicionar(AdicionarDisciplinaNaTurmaParamsV1 params) {
        Turma turma = buscarTurmaComponent.buscarPorUid(params.getTurmaUid());

        if (!params.getHorarioFim().isAfter(params.getHorarioInicio())) {
            throw new BusinessException("O horário de término deve ser maior que o horário de início.");
        }

        Disciplina disciplina = buscarDisciplinaComponent.buscarPorUid(params.getDisciplinaUid());

        Long jaVinculadas = em()
                .createQuery("select count(v) from TurmaDisciplina v where v.turma = :turma and v.disciplina = :disciplina", Long.class)
                .setParameter("turma", turma)
                .setParameter("disciplina", disciplina)
                .getSingleResult();
        if (jaVinculadas > 0) {
            throw new BusinessException("Esta disciplina já está vinculada à turma.");
        }

        Professor professor = buscarProfessorComponent.buscarPorId(params.getProfessorId());

        if (!verificarProfessorHabilitadoComponent.habilitado(professor, disciplina.getId())) {
            throw new BusinessException("O professor selecionado não está habilitado a lecionar a disciplina informada.");
        }

        TurmaDisciplina vinculo = new TurmaDisciplina();

        vinculo.setTurma(turma);
        vinculo.setDisciplina(disciplina);
        vinculo.setProfessor(professor);
        vinculo.setDiaSemana(params.getDiaSemana());
        vinculo.setHorarioInicio(params.getHorarioInicio());
        vinculo.setHorarioFim(params.getHorarioFim());
        vinculo.persist();

        return new Uid(vinculo.getUid());
    }
}
