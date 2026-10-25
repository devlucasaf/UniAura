package erp.uniaura.services.web.funcionario.cadastrarFuncionario.v1;

import erp.uniaura.persistence.entities.CargoFuncionario;

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
public class CadastrarFuncionarioParamsV1 {

    @Schema(description = "Nome do funcionário (vai para o usuário vinculado)")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    private String nome;

    @Schema(description = "E-mail do funcionário, usado no login")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
    private String email;

    @Schema(description = "Senha inicial (opcional); de 6 a 100 caracteres")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
    private String senha;

    @Schema(description = "CPF do funcionário")
    @Size(max = 14, message = "O CPF deve ter no máximo 14 caracteres.")
    private String cpf;

    @Schema(description = "Telefone do funcionário")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
    private String telefone;

    @Schema(description = "Data de nascimento")
    private LocalDate dataNascimento;

    @Schema(description = "Cargo; define também o perfil de acesso do usuário")
    @NotNull(message = "O cargo é obrigatório.")
    private CargoFuncionario cargo;

    @Schema(description = "Data de admissão")
    @NotNull(message = "A data de admissão é obrigatória.")
    private LocalDate dataAdmissao;

    @Schema(description = "Departamento")
    @Size(max = 100, message = "O departamento deve ter no máximo 100 caracteres.")
    private String departamento;
}
