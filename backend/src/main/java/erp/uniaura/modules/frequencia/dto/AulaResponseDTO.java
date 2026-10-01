package erp.uniaura.modules.frequencia.dto;

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
public class AulaResponseDTO {

    private Long            id;
    private Long            turmaDisciplinaId;
    private Long            turmaId;
    private String          turmaCodigo;
    private Long            disciplinaId;
    private String          disciplinaNome;
    private Long            professorId;
    private String          professorNome;
    private LocalDate       dataAula;
    private String          conteudoMinistrado;
    private Integer         totalAlunos;
    private LocalDateTime   criadaEm;
}

