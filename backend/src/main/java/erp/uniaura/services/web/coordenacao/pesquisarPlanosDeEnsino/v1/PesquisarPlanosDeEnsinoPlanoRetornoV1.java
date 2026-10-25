package erp.uniaura.services.web.coordenacao.pesquisarPlanosDeEnsino.v1;

import erp.uniaura.persistence.entities.StatusPlanoEnsino;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarPlanosDeEnsinoPlanoRetornoV1 {

    @Schema(description = "Identificador público do plano")
    private Long                uid;
    private Long                turmaDisciplinaUid;
    private Long                turmaUid;
    private String              turmaCodigo;
    private String              periodoLetivo;
    private Long                disciplinaUid;
    private String              disciplinaCodigo;
    private String              disciplinaNome;
    private Long                professorUid;
    private String              professorNome;
    private StatusPlanoEnsino   status;
    private String              parecer;
    private LocalDateTime       submetidoEm;
    private LocalDateTime       avaliadoEm;
    private LocalDateTime       atualizadoEm;
}
