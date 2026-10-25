package erp.uniaura.services.web.autenticacao.redefinirSenha.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedefinirSenhaParamsV1 {

    @Schema(description = "E-mail de acesso")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @Schema(description = "Código de 6 números recebido na solicitação")
    @NotBlank(message = "O código é obrigatório.")
    @Pattern(regexp = "\\d{6}", message = "O código deve ter 6 números.")
    private String codigo;

    @Schema(description = "Nova senha, de 6 a 100 caracteres")
    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 6, max = 100, message = "A nova senha deve ter entre 6 e 100 caracteres.")
    private String novaSenha;
}
