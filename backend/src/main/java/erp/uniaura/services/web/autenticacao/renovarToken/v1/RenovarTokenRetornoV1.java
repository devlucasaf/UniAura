package erp.uniaura.services.web.autenticacao.renovarToken.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenovarTokenRetornoV1 {

    @Schema(description = "Novo token de acesso (JWT)")
    private String token;

    @Schema(description = "Novo refresh token")
    private String refreshToken;
}
