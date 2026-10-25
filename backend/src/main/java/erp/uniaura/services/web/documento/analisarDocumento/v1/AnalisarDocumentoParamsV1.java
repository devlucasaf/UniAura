package erp.uniaura.services.web.documento.analisarDocumento.v1;

import erp.uniaura.persistence.entities.StatusDocumento;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalisarDocumentoParamsV1 {

    @Schema(description = "Identificador público do documento")
    @NotNull(message = "O uid do documento é obrigatório.")
    private Long uid;

    @Schema(description = "Resultado da análise (aprovado ou rejeitado)")
    @NotNull(message = "O status é obrigatório.")
    private StatusDocumento status;

    @Schema(description = "Observações da análise, por exemplo o motivo da rejeição")
    @Size(max = 500, message = "As observações devem ter no máximo 500 caracteres.")
    private String observacoes;
}
