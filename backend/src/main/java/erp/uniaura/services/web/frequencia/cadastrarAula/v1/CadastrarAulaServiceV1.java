package erp.uniaura.services.web.frequencia.cadastrarAula.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aula;
import erp.uniaura.persistence.entities.Frequencia;
import erp.uniaura.persistence.entities.Matricula;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.StatusMatricula;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;
import erp.uniaura.services.common.professor.VerificarProfessorDaDisciplinaComponent;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CadastrarAulaServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;
    private final BuscarProfessorComponent buscarProfessorComponent;
    private final VerificarProfessorDaDisciplinaComponent verificarProfessorDaDisciplinaComponent;

    // --- CADASTRA A AULA E CRIA A FREQUÊNCIA DE TODOS OS ALUNOS MATRICULADOS COMO PRESENTES ---
    @Transactional
    public CadastrarAulaRetornoV1 cadastrar(Long usuarioId, CadastrarAulaParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid());
        Professor responsavel = verificarProfessorDaDisciplinaComponent.validar(turmaDisciplina, autenticado);

        Professor professor = responsavel;
        if (params.getProfessorUid() != null) {
            professor = buscarProfessorComponent.buscarPorUid(params.getProfessorUid());
        }

        Aula aula = new Aula();

        aula.setTurmaDisciplina(turmaDisciplina);
        aula.setDataAula(params.getDataAula());
        aula.setConteudoMinistrado(params.getConteudoMinistrado());
        aula.setProfessor(professor);
        aula.persist();

        List<Matricula> matriculasAtivas = em()
                .createQuery("select m from Matricula m where m.turma = :turma and m.status = :status", Matricula.class)
                .setParameter("turma", turmaDisciplina.getTurma())
                .setParameter("status", StatusMatricula.ATIVA)
                .getResultList();

        // --- TODOS COMEÇAM PRESENTES; A CHAMADA MARCA AS FALTAS ---
        for (Matricula matricula : matriculasAtivas) {
            Frequencia frequencia = new Frequencia();

            frequencia.setAula(aula);
            frequencia.setAluno(matricula.getAluno());
            frequencia.setPresente(Boolean.TRUE);
            frequencia.persist();
        }

        CadastrarAulaRetornoV1 retorno = new CadastrarAulaRetornoV1();

        retorno.setUid(aula.getUid());
        retorno.setTotalAlunos(matriculasAtivas.size());

        return retorno;
    }
}
