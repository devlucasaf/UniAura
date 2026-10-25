package erp.uniaura.services.web.funcionario.removerFuncionario.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverFuncionarioParamsV1 {

    @Schema(description = "Identificador público do funcionário a remover")
    @NotNull(message = "O uid do funcionário é obrigatório.")
    private Long uid;
}
