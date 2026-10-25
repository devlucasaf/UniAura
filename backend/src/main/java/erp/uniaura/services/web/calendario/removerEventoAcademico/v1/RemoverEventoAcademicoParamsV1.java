package erp.uniaura.services.web.calendario.removerEventoAcademico.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverEventoAcademicoParamsV1 {

    @Schema(description = "Identificador público do evento a remover")
    @NotNull(message = "O uid do evento é obrigatório.")
    private Long uid;
}
