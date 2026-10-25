package erp.uniaura.services.web.nota.pesquisarNotasDaTurmaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarNotasDaTurmaDisciplinaRetornoV1 {

    @Schema(description = "Total de notas que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Notas da página pedida")
    private List<PesquisarNotasDaTurmaDisciplinaNotaRetornoV1> lista;
}
