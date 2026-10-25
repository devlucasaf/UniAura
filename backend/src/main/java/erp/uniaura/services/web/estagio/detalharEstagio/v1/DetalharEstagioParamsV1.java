package erp.uniaura.services.web.estagio.detalharEstagio.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharEstagioParamsV1 {

    @Schema(description = "Identificador público do estágio")
    @NotNull(message = "O uid do estágio é obrigatório.")
    private Long uid;
}
