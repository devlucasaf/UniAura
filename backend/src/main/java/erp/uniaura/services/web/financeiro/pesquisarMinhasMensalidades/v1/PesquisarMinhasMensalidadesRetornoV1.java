package erp.uniaura.services.web.financeiro.pesquisarMinhasMensalidades.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMinhasMensalidadesRetornoV1 {

    @Schema(description = "Total de mensalidades do aluno, sem considerar a paginação")
    private Long total;

    @Schema(description = "Mensalidades da página pedida")
    private List<PesquisarMinhasMensalidadesMensalidadeRetornoV1> lista;
}
