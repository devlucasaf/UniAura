package erp.academico.modules.auditoria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogAuditoriaResponseDTO {

    private UUID            id;
    private UUID            usuarioId;
    private String          usuarioNome;
    private String          acao;
    private String          entidade;
    private String          entidadeId;
    private String          detalhes;
    private LocalDateTime   criadoEm;
}
