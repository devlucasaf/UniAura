package erp.uniaura.modules.coordenacao.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanoEnsinoRequestDTO {

    @NotNull
    private UUID    turmaDisciplinaId;

    @NotNull
    @Size(min = 20, max = 4000)
    private String  ementa;

    @NotNull
    @Size(min = 20, max = 4000)
    private String  objetivos;

    @NotNull
    @Size(min = 20, max = 8000)
    private String  conteudoProgramatico;

    @Size(max = 4000)
    private String  metodologia;

    @NotNull
    @Size(min = 20, max = 4000)
    private String  criteriosAvaliacao;

    @NotNull
    @Size(min = 10, max = 4000)
    private String  bibliografiaBasica;

    @Size(max = 4000)
    private String  bibliografiaComplementar;
}
