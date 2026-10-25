package erp.uniaura.services.web.auditoria.pesquisarLogsDeAuditoria.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarLogsDeAuditoriaLogRetornoV1 {

    @Schema(description = "Identificador público do registro de auditoria")
    private Long            uid;

    @Schema(description = "Identificador público do usuário que fez a ação; vazio quando não havia usuário logado")
    private Long            usuarioUid;
    private String          usuarioNome;
    private String          acao;
    private String          entidade;
    private String          entidadeId;
    private String          detalhes;
    private LocalDateTime   criadoEm;
}
