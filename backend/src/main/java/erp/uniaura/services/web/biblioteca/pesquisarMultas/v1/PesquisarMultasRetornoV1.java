package erp.uniaura.services.web.biblioteca.pesquisarMultas.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMultasRetornoV1 {

    @Schema(description = "Total de multas no status pedido, ignorando a paginação")
    private Long total;

    @Schema(description = "Multas da página pedida")
    private List<PesquisarMultasMultaRetornoV1> lista;
}
