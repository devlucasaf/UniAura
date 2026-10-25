package erp.uniaura.services.web.frequencia.registrarChamada.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistrarChamadaParamsV1 {

    @Schema(description = "Identificador público da aula")
    @NotNull(message = "O uid da aula é obrigatório.")
    private Long aulaUid;

    @Schema(description = "Presença ou falta de cada aluno")
    @NotEmpty(message = "Informe ao menos um registro de presença.")
    @Valid
    private List<RegistrarChamadaRegistroParamsV1> registros;
}
