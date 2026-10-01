package erp.uniaura.modules.coordenacao.dto;

import erp.uniaura.modules.coordenacao.model.StatusPlanoEnsino;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanoEnsinoResponseDTO {

    private Long                                    id;
    private Long                                    turmaDisciplinaId;
    private Long                                    turmaId;
    private String                                  turmaCodigo;
    private String                                  periodoLetivo;
    private Long                                    disciplinaId;
    private String                                  disciplinaCodigo;
    private String                                  disciplinaNome;
    private Long                                    professorId;
    private String                                  professorNome;
    private String                                  ementa;
    private String                                  objetivos;
    private String                                  conteudoProgramatico;
    private String                                  metodologia;
    private String                                  criteriosAvaliacao;
    private String                                  bibliografiaBasica;
    private String                                  bibliografiaComplementar;
    private StatusPlanoEnsino                       status;
    private Long                                    avaliadoPorId;
    private String                                  avaliadoPorNome;
    private String                                  parecer;
    private LocalDateTime                           submetidoEm;
    private LocalDateTime                           avaliadoEm;
    private LocalDateTime                           criadoEm;
    private LocalDateTime                           atualizadoEm;
    private List<AvaliacaoPlanoEnsinoResponseDTO>   historico;
}
