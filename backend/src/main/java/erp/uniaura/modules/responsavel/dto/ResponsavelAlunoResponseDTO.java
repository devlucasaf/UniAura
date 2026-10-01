package erp.uniaura.modules.responsavel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsavelAlunoResponseDTO {

    private Long            id;
    private Long            responsavelId;
    private Long            alunoId;
    private String          alunoNome;
    private String          alunoMatriculaRA;
    private String          observacao;
    private LocalDateTime   criadoEm;
}

