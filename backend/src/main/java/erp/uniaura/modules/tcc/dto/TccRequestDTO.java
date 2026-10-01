package erp.uniaura.modules.tcc.dto;

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
public class TccRequestDTO {

    @NotNull(message = "O aluno é obrigatório.")
    private Long alunoId;

    @NotNull(message = "O título é obrigatório.")
    @Size(max = 300)
    private String titulo;

    @NotNull(message = "O professor orientador é obrigatório.")
    private Long professorOrientadorId;

    private Long professorCoorientadorId;

    @NotNull(message = "O período letivo é obrigatório.")
    @Size(max = 10)
    private String periodoLetivo;
}
