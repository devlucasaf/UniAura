package erp.uniaura.modules.coordenacao.dto;

import erp.uniaura.modules.turma.model.Turno;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurmaSemRegenteDTO {

    private Long    turmaId;
    private String  turmaCodigo;
    private String  serie;
    private Turno   turno;
    private String  periodoLetivo;
    private Long    matriculasAtivas;
    private Integer capacidadeMaxima;
}
