package erp.uniaura.services.web.disciplina.pesquisarPrerequisitosDaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarPrerequisitosDaDisciplinaRetornoV1 {

    @Schema(description = "Pré-requisitos da disciplina")
    private List<PesquisarPrerequisitosDaDisciplinaPrerequisitoRetornoV1> lista;
}
