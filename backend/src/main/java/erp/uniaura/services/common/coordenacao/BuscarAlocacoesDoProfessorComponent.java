package erp.uniaura.services.common.coordenacao;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.TurmaDisciplina;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarAlocacoesDoProfessorComponent extends BaseComponent {

    // --- TODAS AS AULAS DO PROFESSOR, DE UM PERÍODO LETIVO OU DE TODOS QUANDO O PERÍODO NÃO É INFORMADO ---
    public List<TurmaDisciplina> buscar(Professor professor, String periodoLetivo) {
        if (periodoLetivo == null) {
            return em()
                    .createQuery("select td from TurmaDisciplina td where td.professor = :professor order by td.diaSemana, td.horarioInicio", TurmaDisciplina.class)
                    .setParameter("professor", professor)
                    .getResultList();
        }

        return em()
                .createQuery("select td from TurmaDisciplina td where td.professor = :professor and td.turma.periodoLetivo = :periodoLetivo order by td.diaSemana, td.horarioInicio", TurmaDisciplina.class)
                .setParameter("professor", professor)
                .setParameter("periodoLetivo", periodoLetivo)
                .getResultList();
    }

    // --- AULAS DO PROFESSOR QUE SE SOBREPÕEM AO DIA E AO HORÁRIO DO VÍNCULO, SEM CONTAR O PRÓPRIO VÍNCULO ---
    public List<TurmaDisciplina> buscarConflitosDeHorario(Professor professor, TurmaDisciplina vinculo) {
        return em()
                .createQuery("select td from TurmaDisciplina td where td.professor = :professor and td.diaSemana = :diaSemana "
                        + "and td.turma.periodoLetivo = :periodoLetivo and td <> :vinculo "
                        + "and td.horarioInicio < :horarioFim and td.horarioFim > :horarioInicio", TurmaDisciplina.class)
                .setParameter("professor", professor)
                .setParameter("diaSemana", vinculo.getDiaSemana())
                .setParameter("periodoLetivo", vinculo.getTurma().getPeriodoLetivo())
                .setParameter("vinculo", vinculo)
                .setParameter("horarioInicio", vinculo.getHorarioInicio())
                .setParameter("horarioFim", vinculo.getHorarioFim())
                .getResultList();
    }
}
