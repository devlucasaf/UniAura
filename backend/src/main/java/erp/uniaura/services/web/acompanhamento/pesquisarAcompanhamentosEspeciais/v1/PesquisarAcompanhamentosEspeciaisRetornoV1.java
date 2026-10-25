package erp.uniaura.services.web.acompanhamento.pesquisarAcompanhamentosEspeciais.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarAcompanhamentosEspeciaisRetornoV1 {

    @Schema(description = "Total de acompanhamentos que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Acompanhamentos da página pedida")
    private List<PesquisarAcompanhamentosEspeciaisAcompanhamentoRetornoV1> lista;
}
