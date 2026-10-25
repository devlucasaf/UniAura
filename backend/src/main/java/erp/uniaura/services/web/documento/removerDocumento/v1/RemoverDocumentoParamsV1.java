package erp.uniaura.services.web.documento.removerDocumento.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverDocumentoParamsV1 {

    @Schema(description = "Identificador público do documento a remover")
    @NotNull(message = "O uid do documento é obrigatório.")
    private Long uid;
}
