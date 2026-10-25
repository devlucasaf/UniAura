package erp.uniaura.services.web.aluno.cadastrarAluno.v1;

import erp.uniaura.persistence.entities.StatusAluno;

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
public class CadastrarAlunoParamsV1 {

    @Schema(description = "Nome completo, de até 150 caracteres")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    private String nome;

    @Schema(description = "E-mail de acesso, único no sistema")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
    private String email;

    @Schema(description = "Senha inicial, de 6 a 100 caracteres; sem valor, gera uma temporária e envia por e-mail")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
    private String senha;

    @Size(max = 14, message = "CPF deve ter no máximo 14 caracteres.")
    private String cpf;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres.")
    private String telefone;

    private LocalDate dataNascimento;

    @Schema(description = "Registro acadêmico (RA), único")
    @NotBlank(message = "A matrícula (RA) é obrigatória.")
    @Size(max = 30, message = "A matrícula deve ter no máximo 30 caracteres.")
    private String matriculaRA;

    @NotNull(message = "A data de ingresso é obrigatória.")
    private LocalDate dataIngresso;

    @NotNull(message = "O status é obrigatório.")
    private StatusAluno status;

    @Schema(description = "Identificador público da turma atual")
    private Long turmaAtualUid;

    @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres.")
    private String observacoes;
}
