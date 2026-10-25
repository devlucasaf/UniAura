package erp.uniaura.services.web.autenticacao.alterarMinhaSenha.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarMinhaSenhaParamsV1 {

    @Schema(description = "Senha atual")
    @NotBlank(message = "A senha atual é obrigatória.")
    private String senhaAtual;

    @Schema(description = "Nova senha, de 6 a 100 caracteres")
    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 6, max = 100, message = "A nova senha deve ter entre 6 e 100 caracteres.")
    private String novaSenha;
}
