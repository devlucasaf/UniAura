package erp.uniaura.services.web.academico.pesquisarDisciplinasDoAluno.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PesquisarDisciplinasDoAlunoSemestreRetornoV1 {

    @Schema(description = "Número do semestre, a partir de 1")
    private Integer semestre;

    @Schema(description = "Título do semestre (ex.: 1º semestre · Fundamentos)")
    private String titulo;

    @Schema(description = "Nomes das disciplinas do semestre")
    private List<String> disciplinas;
}
