package erp.uniaura.services.web.comunicado.removerComunicado.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverComunicadoParamsV1 {

    @Schema(description = "Identificador público do comunicado a remover")
    @NotNull(message = "O uid do comunicado é obrigatório.")
    private Long uid;
}
