package erp.uniaura.services.web.academico.pesquisarDisciplinasDoAluno.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PesquisarDisciplinasDoAlunoCursoCatalogoV1 {

    private String                                              slug;
    private String                                              titulo;
    private String                                              nivel;
    private String                                              modalidade;
    private Integer                                             semestres;
    private Integer                                             cargaHoraria;
    private List<PesquisarDisciplinasDoAlunoSemestreRetornoV1>  matriz;
}
