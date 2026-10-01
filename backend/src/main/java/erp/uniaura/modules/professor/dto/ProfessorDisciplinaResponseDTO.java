package erp.uniaura.modules.professor.dto;

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
public class ProfessorDisciplinaResponseDTO {

    private Long            id;
    private Long            professorId;
    private Long            disciplinaId;
    private LocalDateTime   criadoEm;
}

