package erp.uniaura.services.web.coordenacao.cancelarReuniaoDoColegiado.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelarReuniaoDoColegiadoParamsV1 {

    @Schema(description = "Identificador público da reunião a cancelar")
    @NotNull(message = "O uid da reunião é obrigatório.")
    private Long uid;

    @Schema(description = "Motivo do cancelamento, de até 2000 caracteres")
    @NotBlank(message = "Informe o motivo do cancelamento da reunião.")
    @Size(max = 2000, message = "O motivo deve ter no máximo 2000 caracteres.")
    private String motivo;
}
