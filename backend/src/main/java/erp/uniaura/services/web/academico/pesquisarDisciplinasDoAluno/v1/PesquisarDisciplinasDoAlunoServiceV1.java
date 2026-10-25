package erp.uniaura.services.web.academico.pesquisarDisciplinasDoAluno.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PesquisarDisciplinasDoAlunoServiceV1 extends BaseService {

    private final PesquisarDisciplinasDoAlunoCatalogoFragmentV1 catalogo;

    // --- DEVOLVE A MATRIZ CURRICULAR DO CURSO DO ALUNO AUTENTICADO, COM O SEMESTRE EM CURSO ---
    @Transactional(readOnly = true)
    public PesquisarDisciplinasDoAlunoRetornoV1 pesquisar(Long usuarioId) {
        Aluno aluno = em()
                .createQuery("select a from Aluno a where a.usuario.id = :usuarioId", Aluno.class)
                .setParameter("usuarioId", usuarioId)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BusinessException("Não encontramos o seu cadastro de aluno."));

        if (aluno.getCurso() == null || aluno.getCurso().isBlank()) {
            throw new BusinessException("Seu cadastro não tem curso informado. Procure a secretaria.");
        }

        PesquisarDisciplinasDoAlunoCursoCatalogoV1 curso = catalogo.buscar(aluno.getCurso())
                .orElseThrow(() -> new BusinessException(
                        "Não encontramos a matriz do curso " + aluno.getCurso() + ". Procure a secretaria."));

        PesquisarDisciplinasDoAlunoRetornoV1 retorno = new PesquisarDisciplinasDoAlunoRetornoV1();

        retorno.setSlug(curso.getSlug());
        retorno.setTitulo(curso.getTitulo());
        retorno.setNivel(curso.getNivel());
        retorno.setModalidade(curso.getModalidade());
        retorno.setSemestres(curso.getSemestres());
        retorno.setCargaHoraria(curso.getCargaHoraria());
        retorno.setSemestreAtual(semestreAtual(aluno.getDataIngresso()));

        if (aluno.getTurno() != null) {
            retorno.setTurno(aluno.getTurno().name());
        }

        retorno.setMatriz(curso.getMatriz());

        return retorno;
    }

    // --- SEMESTRE EM CURSO, CONTADO DESDE O INGRESSO ---
    private int semestreAtual(LocalDate ingresso) {
        if (ingresso == null) {
            return 1;
        }

        LocalDate hoje = LocalDate.now();
        int semestreHoje = 2;
        if (hoje.getMonthValue() <= 6) {
            semestreHoje = 1;
        }

        int semestreIngresso = 2;
        if (ingresso.getMonthValue() <= 6) {
            semestreIngresso = 1;
        }

        return Math.max(1, (hoje.getYear() - ingresso.getYear()) * 2 + (semestreHoje - semestreIngresso) + 1);
    }
}
