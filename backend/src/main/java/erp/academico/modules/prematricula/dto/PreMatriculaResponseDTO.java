package erp.academico.modules.prematricula.dto;

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
public class PreMatriculaResponseDTO {
    private UUID    alunoId;
    private String  nome;
    private String  email;
    private String  matriculaRA;
    private String  curso;
}
