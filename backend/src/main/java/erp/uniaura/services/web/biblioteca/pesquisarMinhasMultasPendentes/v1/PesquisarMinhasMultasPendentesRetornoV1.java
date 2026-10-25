package erp.uniaura.services.web.biblioteca.pesquisarMinhasMultasPendentes.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMinhasMultasPendentesRetornoV1 {

    @Schema(description = "Total de multas pendentes do usuário")
    private Long total;

    @Schema(description = "Multas pendentes, da mais recente para a mais antiga")
    private List<PesquisarMinhasMultasPendentesMultaRetornoV1> lista;
}
