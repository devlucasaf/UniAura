package erp.uniaura.services.web.responsavel.pesquisarAlunosDoResponsavel.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarAlunosDoResponsavelRetornoV1 {

    @Schema(description = "Total de alunos vinculados ao responsável")
    private Long total;

    @Schema(description = "Alunos vinculados, do vínculo mais antigo para o mais novo")
    private List<PesquisarAlunosDoResponsavelAlunoRetornoV1> lista;
}
