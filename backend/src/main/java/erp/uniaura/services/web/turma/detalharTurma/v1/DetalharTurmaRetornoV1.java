package erp.uniaura.services.web.turma.detalharTurma.v1;

import erp.uniaura.persistence.entities.Turno;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharTurmaRetornoV1 {

    @Schema(description = "Identificador público da turma")
    private Long          uid;

    @Schema(description = "TRANSITÓRIO: id sequencial, ainda exigido por Matrícula e Coordenação; sai quando elas migrarem")
    private Long          id;

    private String        codigo;
    private Long          cursoUid;
    private String        cursoNome;
    private String        periodoLetivo;
    private String        serie;
    private String        sala;
    private Turno         turno;

    @Schema(description = "TRANSITÓRIO: id do professor regente; vira uid quando Professor migrar")
    private Long          professorRegenteId;

    private String        professorRegenteNome;
    private Integer       capacidadeMaxima;
    private Boolean       ativa;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
