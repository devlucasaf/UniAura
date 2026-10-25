package erp.uniaura.services.web.coordenacao.consultarPainelDaCoordenacao.v1;

import erp.uniaura.persistence.entities.Turno;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultarPainelDaCoordenacaoTurmaSemRegenteRetornoV1 {

    @Schema(description = "Identificador público da turma")
    private Long    turmaUid;
    private String  turmaCodigo;
    private String  serie;
    private Turno   turno;
    private String  periodoLetivo;
    private Long    matriculasAtivas;
    private Integer capacidadeMaxima;
}
