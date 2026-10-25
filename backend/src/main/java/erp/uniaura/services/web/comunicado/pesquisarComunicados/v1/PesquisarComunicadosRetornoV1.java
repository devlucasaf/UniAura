package erp.uniaura.services.web.comunicado.pesquisarComunicados.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarComunicadosRetornoV1 {

    @Schema(description = "Total de comunicados que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Comunicados da página pedida")
    private List<PesquisarComunicadosComunicadoRetornoV1> lista;
}
