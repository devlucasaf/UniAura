package erp.uniaura.services.web.biblioteca.devolverEmprestimo.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DevolverEmprestimoParamsV1 {

    @Schema(description = "Identificador público do empréstimo devolvido")
    @NotNull(message = "O uid do empréstimo é obrigatório.")
    private Long uid;
}
