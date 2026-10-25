package erp.uniaura.services.web.nota.removerNota.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverNotaParamsV1 {

    @Schema(description = "Identificador público da nota a remover")
    @NotNull(message = "O uid da nota é obrigatório.")
    private Long uid;
}
