package erp.uniaura.services.web.comunicado.cadastrarComunicado.v1;

import erp.uniaura.persistence.entities.PublicoAlvoComunicado;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarComunicadoParamsV1 {

    @Schema(description = "Título do comunicado")
    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 150, message = "O título deve ter no máximo 150 caracteres.")
    private String titulo;

    @Schema(description = "Mensagem do comunicado")
    @NotBlank(message = "A mensagem é obrigatória.")
    @Size(max = 2000, message = "A mensagem deve ter no máximo 2000 caracteres.")
    private String mensagem;

    @Schema(description = "Quem deve receber: TODOS, ALUNOS, PROFESSORES ou TURMA")
    @NotNull(message = "O público-alvo é obrigatório.")
    private PublicoAlvoComunicado publicoAlvo;

    @Schema(description = "Identificador público da turma; obrigatório quando o público-alvo é TURMA e ignorado nos outros casos")
    private Long turmaUid;

    @Schema(description = "Se o comunicado é destacado como importante; sem valor, não é")
    private Boolean importante;
}
