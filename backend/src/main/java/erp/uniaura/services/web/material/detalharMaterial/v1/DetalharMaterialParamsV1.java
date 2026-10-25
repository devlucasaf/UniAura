package erp.uniaura.services.web.material.detalharMaterial.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharMaterialParamsV1 {

    @Schema(description = "Identificador público do material")
    @NotNull(message = "O uid do material é obrigatório.")
    private Long uid;
}
