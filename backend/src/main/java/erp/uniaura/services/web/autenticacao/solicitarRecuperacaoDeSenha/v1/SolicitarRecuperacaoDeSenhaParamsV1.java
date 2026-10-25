package erp.uniaura.services.web.autenticacao.solicitarRecuperacaoDeSenha.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SolicitarRecuperacaoDeSenhaParamsV1 {

    @Schema(description = "E-mail de acesso")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @Schema(description = "CPF cadastrado, com ou sem pontuação")
    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @Schema(description = "Data de nascimento cadastrada")
    @NotNull(message = "A data de nascimento é obrigatória.")
    private LocalDate dataNascimento;
}
