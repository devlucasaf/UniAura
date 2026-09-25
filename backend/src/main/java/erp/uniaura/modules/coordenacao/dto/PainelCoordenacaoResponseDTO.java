package erp.uniaura.modules.coordenacao.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PainelCoordenacaoResponseDTO {

    private UUID                        cursoId;
    private String                      cursoNome;
    private String                      periodoLetivo;
    private Map<String, Long>           alunosPorStatusMatricula;
    private Long                        totalAlunosAtivos;
    private Long                        totalTurmas;
    private Long                        turmasAtivas;
    private Long                        turmasSemProfessorRegente;
    private BigDecimal                  ocupacaoMediaPercentual;
    private Long                        totalResultadosAvaliados;
    private Long                        totalAprovacoes;
    private Long                        totalReprovacoes;
    private BigDecimal                  taxaAprovacaoPercentual;
    private BigDecimal                  mediaGeral;
    private BigDecimal                  frequenciaMediaPercentual;
    private Long                        processosPendentes;
    private Long                        manifestacoesPendentes;
    private List<TurmaSemRegenteDTO>    turmasQueExigemAtencao;
}
