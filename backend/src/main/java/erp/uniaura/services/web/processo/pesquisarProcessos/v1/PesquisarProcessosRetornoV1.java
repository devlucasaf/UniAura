package erp.uniaura.services.web.processo.pesquisarProcessos.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarProcessosRetornoV1 {

    @Schema(description = "Total de processos que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Processos da página pedida")
    private List<PesquisarProcessosProcessoRetornoV1> lista;
}
