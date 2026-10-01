package erp.uniaura.modules.matricula.dto;

import erp.uniaura.modules.matricula.model.StatusMatricula;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatriculaResponseDTO {

    private Long            id;
    private Long            alunoId;
    private String          alunoNome;
    private String          alunoMatriculaRA;
    private Long            turmaId;
    private String          turmaCodigo;
    private String          turmaPeriodoLetivo;
    private LocalDate       dataMatricula;
    private StatusMatricula status;
    private String          observacoes;
    private Long            criadaPorId;
    private String          criadaPorNome;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}

