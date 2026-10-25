package erp.uniaura.services.web.usuario.alterarUsuario.v1;

import erp.uniaura.persistence.entities.TipoUsuario;

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
public class AlterarUsuarioParamsV1 {

    @Schema(description = "Identificador público do usuário a alterar")
    @NotNull(message = "O uid do usuário é obrigatório.")
    private Long uid;

    @Schema(description = "Nome completo, de até 150 caracteres")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    private String nome;

    @Schema(description = "E-mail de acesso, único no sistema")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres.")
    private String email;

    @Schema(description = "Nova senha, de 6 a 100 caracteres; sem valor, mantém a atual")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
    private String senha;

    @Schema(description = "CPF, de até 14 caracteres")
    @Size(max = 14, message = "CPF deve ter no máximo 14 caracteres.")
    private String cpf;

    @Schema(description = "Telefone, de até 20 caracteres")
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres.")
    private String telefone;

    private LocalDate dataNascimento;

    @Schema(description = "Se o usuário pode entrar; sem valor, mantém o atual")
    private Boolean ativo;

    @Schema(description = "Perfil de acesso")
    @NotNull(message = "A role é obrigatória.")
    private TipoUsuario role;
}
