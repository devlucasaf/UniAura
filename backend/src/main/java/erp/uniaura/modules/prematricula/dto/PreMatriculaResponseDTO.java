package erp.uniaura.modules.prematricula.dto;

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
public class PreMatriculaResponseDTO {
    private Long    alunoId;
    private String  nome;
    private String  email;
    private String  matriculaRA;
    private String  curso;
}
