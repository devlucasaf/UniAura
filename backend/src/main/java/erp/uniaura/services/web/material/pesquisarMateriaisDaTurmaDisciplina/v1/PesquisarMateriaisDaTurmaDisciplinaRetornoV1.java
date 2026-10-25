package erp.uniaura.services.web.material.pesquisarMateriaisDaTurmaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMateriaisDaTurmaDisciplinaRetornoV1 {

    @Schema(description = "Total de materiais da turma e disciplina, ignorando a paginação")
    private Long total;

    @Schema(description = "Materiais da página pedida")
    private List<PesquisarMateriaisDaTurmaDisciplinaMaterialRetornoV1> lista;
}
