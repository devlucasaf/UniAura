package erp.uniaura.modules.frequencia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrequenciaResponseDTO {

    private Long        id;
    private Long        aulaId;
    private LocalDate   dataAula;
    private Long        alunoId;
    private String      alunoNome;
    private String      alunoMatriculaRA;
    private Boolean     presente;
    private String      justificativa;
}

