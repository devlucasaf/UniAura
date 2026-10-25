package erp.uniaura.services.web.professor.cadastrarProfessor.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CadastrarProfessorParamsV1 {

    @Schema(description = "Nome do professor (vai para o usuário vinculado)")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    private String nome;

    @Schema(description = "E-mail do professor, usado no login")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
    private String email;

    @Schema(description = "Senha inicial (opcional); de 6 a 100 caracteres")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
    private String senha;

    @Schema(description = "CPF do professor")
    @Size(max = 14, message = "O CPF deve ter no máximo 14 caracteres.")
    private String cpf;

    @Schema(description = "Telefone do professor")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
    private String telefone;

    @Schema(description = "Data de nascimento")
    private LocalDate dataNascimento;

    @Schema(description = "Formação acadêmica")
    @Size(max = 200, message = "A formação deve ter no máximo 200 caracteres.")
    private String formacao;

    @Schema(description = "Área de atuação")
    @Size(max = 150, message = "A área de atuação deve ter no máximo 150 caracteres.")
    private String areaAtuacao;

    @Schema(description = "Carga horária semanal contratada, em horas")
    @Positive(message = "A carga horária deve ser positiva.")
    private Integer cargaHorariaSemanal;

    @Schema(description = "Data de admissão")
    @NotNull(message = "A data de admissão é obrigatória.")
    private LocalDate dataAdmissao;

    @Schema(description = "Se o professor está ativo; sem valor, é cadastrado como ativo")
    private Boolean ativo;
}
