package erp.uniaura.services.web.biblioteca.registrarEmprestimo.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarEmprestimoParamsV1 {

    @Schema(description = "Identificador público do usuário (aluno ou professor) que vai receber o livro")
    @NotNull(message = "O uid do usuário é obrigatório.")
    private Long usuarioUid;

    @Schema(description = "Identificador público do exemplar. Informe o uid ou o código de barras (um dos dois)")
    private Long exemplarUid;

    @Schema(description = "Código de barras do exemplar. Informe o uid ou o código de barras (um dos dois)")
    private String codigoBarras;
}
