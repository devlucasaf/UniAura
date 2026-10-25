package erp.uniaura.services.web.frequencia.pesquisarFrequenciasDaAula.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarFrequenciasDaAulaRetornoV1 {

    @Schema(description = "Total de registros da aula, ignorando a paginação")
    private Long total;

    @Schema(description = "Registros da página pedida")
    private List<PesquisarFrequenciasDaAulaFrequenciaRetornoV1> lista;
}
