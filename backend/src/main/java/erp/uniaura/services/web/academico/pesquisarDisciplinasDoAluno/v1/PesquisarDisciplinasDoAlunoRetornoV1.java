package erp.uniaura.services.web.academico.pesquisarDisciplinasDoAluno.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PesquisarDisciplinasDoAlunoRetornoV1 {

    @Schema(description = "Identificador do curso no catálogo (ex.: ciencia-da-computacao)")
    private String slug;

    @Schema(description = "Nome do curso")
    private String titulo;

    @Schema(description = "Nível (Bacharelado, Tecnólogo, Licenciatura...)")
    private String nivel;

    @Schema(description = "Modalidade (Presencial, EAD...)")
    private String modalidade;

    @Schema(description = "Quantidade de semestres do curso")
    private Integer semestres;

    @Schema(description = "Carga horária total do curso, em horas")
    private Integer cargaHoraria;

    @Schema(description = "Semestre em que o aluno está, contado desde o ingresso (pode passar da duração do curso)")
    private Integer semestreAtual;

    @Schema(description = "Turno do aluno (MANHA, TARDE ou NOITE); nulo se não informado")
    private String turno;

    @Schema(description = "Semestres do curso, em ordem, com as disciplinas de cada um")
    private List<PesquisarDisciplinasDoAlunoSemestreRetornoV1> matriz;
}
