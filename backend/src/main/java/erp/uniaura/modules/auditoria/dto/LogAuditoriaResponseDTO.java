package erp.uniaura.modules.auditoria.dto;

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
public class LogAuditoriaResponseDTO {

    private Long            id;
    private Long            usuarioId;
    private String          usuarioNome;
    private String          acao;
    private String          entidade;
    private String          entidadeId;
    private String          detalhes;
    private LocalDateTime   criadoEm;
}
