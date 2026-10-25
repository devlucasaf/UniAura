package erp.uniaura.services.web.coordenacao.detalharReuniaoDoColegiado.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharReuniaoDoColegiadoParamsV1 {

    @Schema(description = "Identificador público da reunião")
    @NotNull(message = "O uid da reunião é obrigatório.")
    private Long uid;
}
