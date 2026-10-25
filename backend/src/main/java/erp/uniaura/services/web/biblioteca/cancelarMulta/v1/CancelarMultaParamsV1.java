package erp.uniaura.services.web.biblioteca.cancelarMulta.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelarMultaParamsV1 {

    @Schema(description = "Identificador público da multa a cancelar")
    @NotNull(message = "O uid da multa é obrigatório.")
    private Long uid;
}
