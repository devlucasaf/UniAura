package erp.uniaura.modules.coordenacao.repository;

import erp.uniaura.modules.turma.model.DiaSemana;
import erp.uniaura.modules.turma.model.TurmaDisciplina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AlocacaoDocenteRepository extends JpaRepository<TurmaDisciplina, UUID> {

    // --- AULAS DO PROFESSOR QUE COLIDEM COM A FAIXA INFORMADA NO MESMO DIA E PERÍODO LETIVO ---
    // --- DUAS FAIXAS SE SOBREPÕEM QUANDO CADA UMA COMEÇA ANTES DO FIM DA OUTRA ---
    @Query("""
            SELECT td FROM TurmaDisciplina td
             WHERE td.professor.id = :professorId
               AND td.diaSemana = :diaSemana
               AND td.turma.periodoLetivo = :periodoLetivo
               AND (:ignorarVinculoId IS NULL OR td.id <> :ignorarVinculoId)
               AND td.horarioInicio < :horarioFim
               AND td.horarioFim > :horarioInicio
            """)
    List<TurmaDisciplina> buscarConflitosDeHorario(@Param("professorId") UUID professorId,
                                                   @Param("diaSemana") DiaSemana diaSemana,
                                                   @Param("periodoLetivo") String periodoLetivo,
                                                   @Param("horarioInicio") LocalTime horarioInicio,
                                                   @Param("horarioFim") LocalTime horarioFim,
                                                   @Param("ignorarVinculoId") UUID ignorarVinculoId);

    // --- TODAS AS AULAS DO PROFESSOR EM UM PERÍODO LETIVO ---
    @Query("""
            SELECT td FROM TurmaDisciplina td
             WHERE td.professor.id = :professorId
               AND (:periodoLetivo IS NULL OR td.turma.periodoLetivo = :periodoLetivo)
             ORDER BY td.diaSemana, td.horarioInicio
            """)
    List<TurmaDisciplina> buscarAlocacoesDoProfessor(@Param("professorId") UUID professorId,
                                                     @Param("periodoLetivo") String periodoLetivo);
}
