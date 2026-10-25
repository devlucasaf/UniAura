package erp.uniaura.services.web.acompanhamento.pesquisarRegistrosDeAcompanhamento.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarRegistrosDeAcompanhamentoRetornoV1 {

    @Schema(description = "Total de registros que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Registros da página pedida")
    private List<PesquisarRegistrosDeAcompanhamentoRegistroRetornoV1> lista;
}
