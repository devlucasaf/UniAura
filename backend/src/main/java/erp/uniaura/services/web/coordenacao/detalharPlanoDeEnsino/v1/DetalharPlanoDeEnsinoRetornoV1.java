package erp.uniaura.services.web.coordenacao.detalharPlanoDeEnsino.v1;

import erp.uniaura.persistence.entities.StatusPlanoEnsino;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DetalharPlanoDeEnsinoRetornoV1 {

    @Schema(description = "Identificador público do plano")
    private Long              uid;
    private Long              turmaDisciplinaUid;
    private Long              turmaUid;
    private String            turmaCodigo;
    private String            periodoLetivo;
    private Long              disciplinaUid;
    private String            disciplinaCodigo;
    private String            disciplinaNome;
    private Long              professorUid;
    private String            professorNome;
    private String            ementa;
    private String            objetivos;
    private String            conteudoProgramatico;
    private String            metodologia;
    private String            criteriosAvaliacao;
    private String            bibliografiaBasica;
    private String            bibliografiaComplementar;
    private StatusPlanoEnsino status;

    @Schema(description = "Nome de quem avaliou; vazio enquanto não houve avaliação")
    private String            avaliadoPorNome;
    private String            parecer;
    private LocalDateTime     submetidoEm;
    private LocalDateTime     avaliadoEm;
    private LocalDateTime     criadoEm;
    private LocalDateTime     atualizadoEm;

    @Schema(description = "Passos do plano, do mais antigo para o mais recente")
    private List<DetalharPlanoDeEnsinoHistoricoRetornoV1> historico;
}
