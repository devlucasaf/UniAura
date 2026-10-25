package erp.uniaura.services.web.biblioteca.removerExemplar.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverExemplarParamsV1 {

    @Schema(description = "Identificador público do exemplar a remover")
    @NotNull(message = "O uid do exemplar é obrigatório.")
    private Long uid;
}
