package erp.uniaura.services.web.financeiro.detalharMensalidade.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharMensalidadeParamsV1 {

    @Schema(description = "Identificador público da mensalidade")
    @NotNull(message = "O uid da mensalidade é obrigatório.")
    private Long uid;
}
