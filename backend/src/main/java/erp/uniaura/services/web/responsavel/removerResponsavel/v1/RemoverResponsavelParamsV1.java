package erp.uniaura.services.web.responsavel.removerResponsavel.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverResponsavelParamsV1 {

    @Schema(description = "Identificador público do responsável a remover")
    @NotNull(message = "O uid do responsável é obrigatório.")
    private Long uid;
}
