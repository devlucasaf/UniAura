package erp.uniaura.services.web.coordenacao.consultarCargaHorariaDoProfessor.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultarCargaHorariaDoProfessorParamsV1 {

    @Schema(description = "Identificador público do professor")
    @NotNull(message = "O uid do professor é obrigatório.")
    private Long professorUid;

    @Schema(description = "Período letivo, por exemplo 2026.1; sem valor, soma todos os períodos")
    private String periodoLetivo;
}
