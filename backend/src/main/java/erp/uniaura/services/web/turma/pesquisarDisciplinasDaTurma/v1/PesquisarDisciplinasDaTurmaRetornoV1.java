package erp.uniaura.services.web.turma.pesquisarDisciplinasDaTurma.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarDisciplinasDaTurmaRetornoV1 {

    @Schema(description = "Disciplinas vinculadas à turma")
    private List<PesquisarDisciplinasDaTurmaDisciplinaRetornoV1> lista;
}
