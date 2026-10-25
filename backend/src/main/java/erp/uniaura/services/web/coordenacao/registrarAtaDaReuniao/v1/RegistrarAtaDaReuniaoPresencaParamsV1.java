package erp.uniaura.services.web.coordenacao.registrarAtaDaReuniao.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarAtaDaReuniaoPresencaParamsV1 {

    @Schema(description = "Identificador público do usuário convocado")
    @NotNull(message = "O uid do usuário é obrigatório.")
    private Long usuarioUid;

    @Schema(description = "Se o usuário esteve presente")
    @NotNull(message = "Informe se o usuário esteve presente.")
    private Boolean presente;
}
