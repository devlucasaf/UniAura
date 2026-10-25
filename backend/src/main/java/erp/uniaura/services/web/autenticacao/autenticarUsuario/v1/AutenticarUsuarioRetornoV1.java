package erp.uniaura.services.web.autenticacao.autenticarUsuario.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticarUsuarioRetornoV1 {

    @Schema(description = "Token de acesso")
    private String token;

    @Schema(description = "Token para renovar o acesso sem pedir a senha de novo")
    private String refreshToken;
    private AutenticarUsuarioUsuarioRetornoV1 usuario;
}
