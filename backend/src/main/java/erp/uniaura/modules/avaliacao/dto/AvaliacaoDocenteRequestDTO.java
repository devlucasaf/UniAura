package erp.uniaura.modules.avaliacao.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoDocenteRequestDTO {

    @NotNull(message = "O vínculo turma/disciplina é obrigatório.")
    private Long turmaDisciplinaId;

    @NotNull(message = "A nota de didática é obrigatória.")
    @Min(1) @Max(5)
    private Integer notaDidatica;

    @NotNull(message = "A nota de pontualidade é obrigatória.")
    @Min(1) @Max(5)
    private Integer notaPontualidade;

    @NotNull(message = "A nota de disponibilidade é obrigatória.")
    @Min(1) @Max(5)
    private Integer notaDisponibilidade;

    @Size(max = 1000)
    private String comentario;
}
