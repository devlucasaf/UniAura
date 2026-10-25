package erp.uniaura.services.web.tcc.enviarEntregaDeTcc.v1;

import erp.uniaura.persistence.entities.TipoEntregaTcc;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnviarEntregaDeTccParamsV1 {

    @Schema(description = "Identificador público do TCC")
    @NotNull(message = "O uid do TCC é obrigatório.")
    private Long tccUid;

    @Schema(description = "Tipo da entrega: PROJETO, PARCIAL ou VERSAO_FINAL")
    @NotNull(message = "O tipo da entrega é obrigatório.")
    private TipoEntregaTcc tipo;
}
