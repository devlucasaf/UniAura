package erp.uniaura.services.web.coordenacao.detalharPlanoDeEnsino.v1;

import erp.uniaura.persistence.entities.StatusPlanoEnsino;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharPlanoDeEnsinoHistoricoRetornoV1 {

    @Schema(description = "Identificador público do registro do histórico")
    private Long                uid;
    private String              autorNome;
    private StatusPlanoEnsino   statusAnterior;
    private StatusPlanoEnsino   statusNovo;
    private String              parecer;
    private LocalDateTime       criadoEm;
}
