package erp.uniaura.services.web.tcc.pesquisarEntregasDoTcc.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarEntregasDoTccRetornoV1 {

    @Schema(description = "Total de entregas do TCC, ignorando a paginação")
    private Long total;

    @Schema(description = "Entregas da página pedida")
    private List<PesquisarEntregasDoTccEntregaRetornoV1> lista;
}
