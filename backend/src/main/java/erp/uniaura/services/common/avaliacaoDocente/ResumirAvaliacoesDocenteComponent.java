package erp.uniaura.services.common.avaliacaoDocente;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.TurmaDisciplina;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ResumirAvaliacoesDocenteComponent extends BaseComponent {

    private static final int ESCALA = 2;

    // --- MÉDIAS DAS AVALIAÇÕES DE UMA TURMA/DISCIPLINA ---
    public ResumoDeAvaliacaoDocente resumirDaTurmaDisciplina(TurmaDisciplina turmaDisciplina) {
        Object[] linha = em()
                .createQuery("""
                        select count(a), coalesce(avg(a.notaDidatica), 0), coalesce(avg(a.notaPontualidade), 0),
                               coalesce(avg(a.notaDisponibilidade), 0)
                          from AvaliacaoDocente a
                         where a.turmaDisciplina = :turmaDisciplina
                        """, Object[].class)
                .setParameter("turmaDisciplina", turmaDisciplina)
                .getSingleResult();

        return montar(linha);
    }

    // --- MÉDIAS CONSOLIDADAS DE TODAS AS TURMAS/DISCIPLINAS DE UM PROFESSOR ---
    public ResumoDeAvaliacaoDocente resumirDoProfessor(Professor professor) {
        Object[] linha = em()
                .createQuery("""
                        select count(a), coalesce(avg(a.notaDidatica), 0), coalesce(avg(a.notaPontualidade), 0),
                               coalesce(avg(a.notaDisponibilidade), 0)
                          from AvaliacaoDocente a
                         where a.turmaDisciplina.professor = :professor
                        """, Object[].class)
                .setParameter("professor", professor)
                .getSingleResult();

        return montar(linha);
    }

    // --- CONVERTE A LINHA AGREGADA NO RESUMO ---
    private ResumoDeAvaliacaoDocente montar(Object[] linha) {
        long quantidade = ((Number) linha[0]).longValue();
        BigDecimal mediaDidatica = arredondar(linha[1]);
        BigDecimal mediaPontualidade = arredondar(linha[2]);
        BigDecimal mediaDisponibilidade = arredondar(linha[3]);
        BigDecimal mediaGeral = mediaDidatica.add(mediaPontualidade)
                .add(mediaDisponibilidade)
                .divide(BigDecimal.valueOf(3), ESCALA, RoundingMode.HALF_UP);

        return new ResumoDeAvaliacaoDocente(quantidade, mediaDidatica, mediaPontualidade, mediaDisponibilidade, mediaGeral);
    }

    // --- ARREDONDA O VALOR PARA A ESCALA DEFINIDA ---
    private BigDecimal arredondar(Object valor) {
        return new BigDecimal(valor.toString()).setScale(ESCALA, RoundingMode.HALF_UP);
    }
}
