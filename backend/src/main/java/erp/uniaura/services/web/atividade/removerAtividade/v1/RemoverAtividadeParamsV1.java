package erp.uniaura.services.web.atividade.removerAtividade.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverAtividadeParamsV1 {

    @Schema(description = "Identificador público da atividade a remover")
    @NotNull(message = "O uid da atividade é obrigatório.")
    private Long uid;
}
