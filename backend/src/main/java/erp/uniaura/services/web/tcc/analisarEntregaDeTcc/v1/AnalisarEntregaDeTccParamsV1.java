package erp.uniaura.services.web.tcc.analisarEntregaDeTcc.v1;

import erp.uniaura.persistence.entities.StatusEntregaTcc;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalisarEntregaDeTccParamsV1 {

    @Schema(description = "Identificador público da entrega")
    @NotNull(message = "O uid da entrega é obrigatório.")
    private Long entregaUid;

    @Schema(description = "Resultado da análise: APROVADA ou REJEITADA")
    @NotNull(message = "O status da análise é obrigatório.")
    private StatusEntregaTcc status;

    @Schema(description = "Observações do orientador")
    @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres.")
    private String observacoesOrientador;
}
