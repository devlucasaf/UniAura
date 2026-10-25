package erp.uniaura.services.web.coordenacao.submeterPlanoDeEnsino.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmeterPlanoDeEnsinoParamsV1 {

    @Schema(description = "Identificador público do plano a submeter")
    @NotNull(message = "O uid do plano é obrigatório.")
    private Long uid;
}
