package erp.uniaura.services.web.material.removerMaterial.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverMaterialParamsV1 {

    @Schema(description = "Identificador público do material a remover")
    @NotNull(message = "O uid do material é obrigatório.")
    private Long uid;
}
