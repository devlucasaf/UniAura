package erp.uniaura.services.web.estagio.analisarRelatorioDeEstagio.v1;

import erp.uniaura.persistence.entities.StatusRelatorioEstagio;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalisarRelatorioDeEstagioParamsV1 {

    @Schema(description = "Identificador público do relatório")
    @NotNull(message = "O uid do relatório é obrigatório.")
    private Long relatorioUid;

    @Schema(description = "Resultado da análise: APROVADO ou REJEITADO")
    @NotNull(message = "O status da análise é obrigatório.")
    private StatusRelatorioEstagio status;

    @Schema(description = "Observações da análise, por exemplo o motivo da rejeição")
    @Size(max = 500, message = "As observações devem ter no máximo 500 caracteres.")
    private String observacoes;
}
