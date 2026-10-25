package erp.uniaura.services.web.tcc.convocarMembroDaBanca.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvocarMembroDaBancaParamsV1 {

    @Schema(description = "Identificador público do TCC")
    @NotNull(message = "O uid do TCC é obrigatório.")
    private Long tccUid;

    @Schema(description = "Identificador público do professor convocado")
    @NotNull(message = "O professor é obrigatório.")
    private Long professorUid;
}
