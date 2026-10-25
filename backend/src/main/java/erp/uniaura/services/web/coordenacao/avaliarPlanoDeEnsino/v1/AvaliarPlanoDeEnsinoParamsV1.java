package erp.uniaura.services.web.coordenacao.avaliarPlanoDeEnsino.v1;

import erp.uniaura.persistence.entities.StatusPlanoEnsino;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvaliarPlanoDeEnsinoParamsV1 {

    @Schema(description = "Identificador público do plano a avaliar")
    @NotNull(message = "O uid do plano é obrigatório.")
    private Long uid;

    @Schema(description = "Desfecho da avaliação: APROVADO, DEVOLVIDO ou REPROVADO")
    @NotNull(message = "O desfecho da avaliação é obrigatório.")
    private StatusPlanoEnsino status;

    @Schema(description = "Parecer da coordenação; obrigatório ao devolver ou reprovar")
    @Size(max = 4000, message = "O parecer deve ter no máximo 4000 caracteres.")
    private String parecer;
}
