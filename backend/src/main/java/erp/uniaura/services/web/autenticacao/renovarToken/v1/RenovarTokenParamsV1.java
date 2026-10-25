package erp.uniaura.services.web.autenticacao.renovarToken.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenovarTokenParamsV1 {

    @Schema(description = "Refresh token recebido no login")
    @NotBlank(message = "O refresh token é obrigatório.")
    private String refreshToken;
}
