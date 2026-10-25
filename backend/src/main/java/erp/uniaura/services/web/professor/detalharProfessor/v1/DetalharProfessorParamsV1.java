package erp.uniaura.services.web.professor.detalharProfessor.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharProfessorParamsV1 {

    @Schema(description = "Identificador público do professor")
    @NotNull(message = "O uid do professor é obrigatório.")
    private Long uid;
}
