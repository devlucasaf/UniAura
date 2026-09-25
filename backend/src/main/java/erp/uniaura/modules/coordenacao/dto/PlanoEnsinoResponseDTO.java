package erp.uniaura.modules.coordenacao.dto;

import erp.uniaura.modules.coordenacao.model.StatusPlanoEnsino;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanoEnsinoResponseDTO {

    private UUID                                    id;
    private UUID                                    turmaDisciplinaId;
    private UUID                                    turmaId;
    private String                                  turmaCodigo;
    private String                                  periodoLetivo;
    private UUID                                    disciplinaId;
    private String                                  disciplinaCodigo;
    private String                                  disciplinaNome;
    private UUID                                    professorId;
    private String                                  professorNome;

    private String                                  ementa;
    private String                                  objetivos;
    private String                                  conteudoProgramatico;
    private String                                  metodologia;
    private String                                  criteriosAvaliacao;
    private String                                  bibliografiaBasica;
    private String                                  bibliografiaComplementar;

    private StatusPlanoEnsino                       status;
    private UUID                                    avaliadoPorId;
    private String                                  avaliadoPorNome;
    private String                                  parecer;
    private LocalDateTime                           submetidoEm;
    private LocalDateTime                           avaliadoEm;
    private LocalDateTime                           criadoEm;
    private LocalDateTime                           atualizadoEm;

    // --- PREENCHIDO APENAS NA CONSULTA DE UM PLANO ESPECÍFICO ---
    private List<AvaliacaoPlanoEnsinoResponseDTO>   historico;
}
