package erp.uniaura.services.web.avaliacaoDocente.pesquisarComentariosDaTurmaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarComentariosDaTurmaDisciplinaRetornoV1 {

    @Schema(description = "Total de comentários do vínculo, ignorando a paginação")
    private Long total;

    @Schema(description = "Comentários da página pedida")
    private List<PesquisarComentariosDaTurmaDisciplinaComentarioRetornoV1> lista;
}
