package erp.uniaura.services.web.avaliacaoDocente.registrarAvaliacaoDocente.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.AvaliacaoDocente;
import erp.uniaura.persistence.entities.StatusMatricula;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrarAvaliacaoDocenteServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;

    // --- REGISTRA A AVALIAÇÃO DO DOCENTE APÓS VALIDAR MATRÍCULA E DUPLICIDADE ---
    @Transactional
    public void registrar(Long usuarioId, RegistrarAvaliacaoDocenteParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);
        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid());

        Long matriculasAtivas = em()
                .createQuery("select count(m) from Matricula m where m.aluno = :aluno and m.turma = :turma and m.status = :status", Long.class)
                .setParameter("aluno", aluno)
                .setParameter("turma", turmaDisciplina.getTurma())
                .setParameter("status", StatusMatricula.ATIVA)
                .getSingleResult();

        if (matriculasAtivas == 0) {
            throw new BusinessException("Você só pode avaliar disciplinas de turmas em que está matriculado.");
        }

        Long jaAvaliadas = em()
                .createQuery("select count(a) from AvaliacaoDocente a where a.aluno = :aluno and a.turmaDisciplina = :turmaDisciplina", Long.class)
                .setParameter("aluno", aluno)
                .setParameter("turmaDisciplina", turmaDisciplina)
                .getSingleResult();

        if (jaAvaliadas > 0) {
            throw new BusinessException("Você já avaliou esta disciplina.");
        }

        AvaliacaoDocente avaliacao = new AvaliacaoDocente();

        avaliacao.setAluno(aluno);
        avaliacao.setTurmaDisciplina(turmaDisciplina);
        avaliacao.setNotaDidatica(params.getNotaDidatica());
        avaliacao.setNotaPontualidade(params.getNotaPontualidade());
        avaliacao.setNotaDisponibilidade(params.getNotaDisponibilidade());
        avaliacao.setComentario(params.getComentario());
        avaliacao.persist();
    }
}
