package erp.uniaura.services.web.usuario.detalharUsuario.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharUsuarioParamsV1 {

    @Schema(description = "Identificador público do usuário")
    @NotNull(message = "O uid do usuário é obrigatório.")
    private Long uid;
}
