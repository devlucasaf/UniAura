package erp.uniaura.services.web.coordenacao.consultarCargaHorariaDoProfessor.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ConsultarCargaHorariaDoProfessorRetornoV1 {

    @Schema(description = "Identificador público do professor")
    private Long       professorUid;
    private String     professorNome;
    private String     periodoLetivo;

    @Schema(description = "Horas semanais já comprometidas com aulas")
    private BigDecimal horasSemanaisAlocadas;

    @Schema(description = "Carga horária semanal do contrato; vazio se não foi cadastrada")
    private Integer    cargaHorariaSemanalContratada;

    @Schema(description = "Horas semanais ainda livres; vazio se não há carga contratada")
    private BigDecimal horasSemanaisDisponiveis;
    private List<ConsultarCargaHorariaDoProfessorAlocacaoRetornoV1> alocacoes;
}
