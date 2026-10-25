package erp.uniaura.services.web.coordenacao.agendarReuniaoDoColegiado.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendarReuniaoDoColegiadoParamsV1 {

    @Schema(description = "Identificador público do curso do colegiado")
    @NotNull(message = "O uid do curso é obrigatório.")
    private Long cursoUid;

    @Schema(description = "Título da reunião, de 5 a 200 caracteres")
    @NotNull(message = "O título é obrigatório.")
    @Size(min = 5, max = 200, message = "O título deve ter de 5 a 200 caracteres.")
    private String titulo;

    @Schema(description = "Data e hora da reunião")
    @NotNull(message = "A data e a hora são obrigatórias.")
    private LocalDateTime dataHora;

    @Schema(description = "Local da reunião, de até 150 caracteres")
    @Size(max = 150, message = "O local deve ter no máximo 150 caracteres.")
    private String local;

    @Schema(description = "Pauta, de 10 a 8000 caracteres")
    @NotNull(message = "A pauta é obrigatória.")
    @Size(min = 10, max = 8000, message = "A pauta deve ter de 10 a 8000 caracteres.")
    private String pauta;
}
