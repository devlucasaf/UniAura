package erp.uniaura.modules.nota.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoletimResponseDTO {

    private Long                    alunoId;
    private String                  alunoNome;
    private String                  alunoMatriculaRA;
    private String                  periodoLetivo;
    private List<DisciplinaBoletim> disciplinas;

}
