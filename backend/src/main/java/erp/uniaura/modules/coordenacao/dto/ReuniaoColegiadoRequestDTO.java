package erp.uniaura.modules.coordenacao.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReuniaoColegiadoRequestDTO {

    @NotNull
    private UUID            cursoId;

    @NotNull
    @Size(min = 5, max = 200)
    private String          titulo;

    @NotNull
    private LocalDateTime   dataHora;

    @Size(max = 150)
    private String          local;

    @NotNull
    @Size(min = 10, max = 8000)
    private String          pauta;
}
