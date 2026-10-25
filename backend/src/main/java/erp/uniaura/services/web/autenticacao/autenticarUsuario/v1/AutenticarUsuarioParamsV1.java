package erp.uniaura.services.web.autenticacao.autenticarUsuario.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutenticarUsuarioParamsV1 {

    @Schema(description = "E-mail de acesso")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @Schema(description = "Senha do usuário")
    @NotBlank(message = "A senha é obrigatória.")
    private String senha;
}
