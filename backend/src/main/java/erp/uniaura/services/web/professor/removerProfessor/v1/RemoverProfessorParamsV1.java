package erp.uniaura.services.web.professor.removerProfessor.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverProfessorParamsV1 {

    @Schema(description = "Identificador público do professor a remover")
    @NotNull(message = "O uid do professor é obrigatório.")
    private Long uid;
}
