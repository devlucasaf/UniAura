package erp.uniaura.services.web.curso.pesquisarCursos.v1;

import erp.uniaura.persistence.entities.NivelCurso;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarCursosCursoRetornoV1 {

    @Schema(description = "Identificador público do curso")
    private Long      uid;

    @Schema(description = "TRANSITÓRIO: id sequencial, ainda exigido por Disciplina e Turma; sai quando elas migrarem")
    private Long        id;

    private String      nome;
    private NivelCurso  nivel;
    private Integer     duracaoSemestres;
    private Integer     cargaHorariaTotal;
    private Boolean     ativo;
}
