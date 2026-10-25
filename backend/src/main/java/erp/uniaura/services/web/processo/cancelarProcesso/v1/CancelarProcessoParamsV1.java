package erp.uniaura.services.web.processo.cancelarProcesso.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelarProcessoParamsV1 {

    @Schema(description = "Identificador público do processo")
    @NotNull(message = "O uid do processo é obrigatório.")
    private Long uid;

    @Schema(description = "Motivo do cancelamento; se vazio, registra 'Cancelado pelo aluno.'")
    @Size(max = 4000, message = "O motivo deve ter no máximo 4000 caracteres.")
    private String motivo;
}
