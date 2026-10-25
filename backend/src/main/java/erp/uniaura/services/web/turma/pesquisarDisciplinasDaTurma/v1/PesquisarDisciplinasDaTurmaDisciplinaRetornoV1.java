package erp.uniaura.services.web.turma.pesquisarDisciplinasDaTurma.v1;

import erp.uniaura.persistence.entities.DiaSemana;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class PesquisarDisciplinasDaTurmaDisciplinaRetornoV1 {

    @Schema(description = "Identificador público do vínculo entre a turma e a disciplina")
    private Long          uid;

    @Schema(description = "TRANSITÓRIO: id sequencial do vínculo, ainda exigido por Atividade, Avaliação, Frequência, Material, Nota e Coordenação; sai quando eles migrarem")
    private Long          id;

    @Schema(description = "Identificador público da disciplina")
    private Long          disciplinaUid;

    private String        disciplinaCodigo;
    private String        disciplinaNome;

    @Schema(description = "TRANSITÓRIO: id do professor responsável; vira uid quando Professor migrar")
    private Long          professorId;

    private String        professorNome;
    private DiaSemana     diaSemana;
    private LocalTime     horarioInicio;
    private LocalTime     horarioFim;
    private LocalDateTime criadoEm;
}
