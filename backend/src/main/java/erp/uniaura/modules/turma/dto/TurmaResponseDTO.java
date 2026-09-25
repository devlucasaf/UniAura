package erp.uniaura.modules.turma.dto;

import erp.uniaura.modules.turma.model.Turno;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurmaResponseDTO {

    private UUID            id;
    private String          codigo;
    private UUID            cursoId;
    private String          cursoNome;
    private String          periodoLetivo;
    private String          serie;
    private String          sala;
    private Turno           turno;
    private UUID            professorRegenteId;
    private String          professorRegenteNome;
    private Integer         capacidadeMaxima;
    private Boolean         ativa;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}

