package erp.uniaura.modules.disciplina.dto;

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
public class DisciplinaPrerequisitoResponseDTO {

    private Long            id;
    private Long            disciplinaId;
    private Long            prerequisitoId;
    private String          prerequisitoCodigo;
    private String          prerequisitoNome;
    private LocalDateTime   criadoEm;
}

