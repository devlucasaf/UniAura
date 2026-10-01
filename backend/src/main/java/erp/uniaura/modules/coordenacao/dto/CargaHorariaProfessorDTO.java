package erp.uniaura.modules.coordenacao.dto;

import erp.uniaura.modules.turma.dto.TurmaDisciplinaResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargaHorariaProfessorDTO {

    private Long                                professorId;
    private String                              professorNome;
    private String                              periodoLetivo;
    private BigDecimal                          horasSemanaisAlocadas;
    private Integer                             cargaHorariaSemanalContratada;
    private BigDecimal                          horasSemanaisDisponiveis;
    private List<TurmaDisciplinaResponseDTO>    alocacoes;
}
