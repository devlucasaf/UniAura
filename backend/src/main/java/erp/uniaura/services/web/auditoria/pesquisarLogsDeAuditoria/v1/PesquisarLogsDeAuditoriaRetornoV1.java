package erp.uniaura.services.web.auditoria.pesquisarLogsDeAuditoria.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarLogsDeAuditoriaRetornoV1 {

    @Schema(description = "Total de registros que atendem ao filtro, sem considerar a paginação")
    private Long total;

    @Schema(description = "Registros da página pedida")
    private List<PesquisarLogsDeAuditoriaLogRetornoV1> lista;
}
