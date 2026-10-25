package erp.uniaura.services.web.responsavel.alterarResponsavel.v1;

import erp.uniaura.persistence.entities.Parentesco;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AlterarResponsavelParamsV1 {

    @Schema(description = "Identificador público do responsável a alterar")
    @NotNull(message = "O uid do responsável é obrigatório.")
    private Long uid;

    @Schema(description = "Nome do responsável (vai para o usuário vinculado)")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    private String nome;

    @Schema(description = "E-mail do responsável, usado no login")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
    private String email;

    @Schema(description = "CPF do responsável")
    @Size(max = 14, message = "O CPF deve ter no máximo 14 caracteres.")
    private String cpf;

    @Schema(description = "Telefone do responsável")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
    private String telefone;

    @Schema(description = "Data de nascimento")
    private LocalDate dataNascimento;

    @Schema(description = "Parentesco com o aluno")
    @NotNull(message = "O parentesco é obrigatório.")
    private Parentesco parentesco;
}
