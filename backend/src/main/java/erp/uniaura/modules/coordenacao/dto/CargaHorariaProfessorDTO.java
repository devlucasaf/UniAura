package erp.uniaura.modules.coordenacao.dto;

import erp.uniaura.modules.turma.dto.TurmaDisciplinaResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CargaHorariaProfessorDTO {

    private UUID                                professorId;
    private String                              professorNome;
    private String                              periodoLetivo;

    // --- SOMA DA DURAÇÃO DAS AULAS SEMANAIS JÁ ALOCADAS ---
    private BigDecimal                          horasSemanaisAlocadas;

    // --- LIMITE CADASTRADO NO PROFESSOR; NULO QUANDO NÃO INFORMADO ---
    private Integer                             cargaHorariaSemanalContratada;
    private BigDecimal                          horasSemanaisDisponiveis;

    private List<TurmaDisciplinaResponseDTO>    alocacoes;
}
