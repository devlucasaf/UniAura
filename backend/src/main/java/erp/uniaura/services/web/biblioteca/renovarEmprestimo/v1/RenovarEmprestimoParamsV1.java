package erp.uniaura.services.web.biblioteca.renovarEmprestimo.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenovarEmprestimoParamsV1 {

    @Schema(description = "Identificador público do empréstimo a renovar")
    @NotNull(message = "O uid do empréstimo é obrigatório.")
    private Long uid;
}
