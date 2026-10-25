package erp.uniaura.services.web.tcc.detalharTcc.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharTccParamsV1 {

    @Schema(description = "Identificador público do TCC")
    @NotNull(message = "O uid do TCC é obrigatório.")
    private Long uid;
}
