package erp.uniaura.services.web.atividade.detalharAtividade.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharAtividadeParamsV1 {

    @Schema(description = "Identificador público da atividade")
    @NotNull(message = "O uid da atividade é obrigatório.")
    private Long uid;
}
