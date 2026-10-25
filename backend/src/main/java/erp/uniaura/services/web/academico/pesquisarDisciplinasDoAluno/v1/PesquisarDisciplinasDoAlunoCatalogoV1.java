package erp.uniaura.services.web.academico.pesquisarDisciplinasDoAluno.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PesquisarDisciplinasDoAlunoCatalogoV1 {

    private List<PesquisarDisciplinasDoAlunoCursoCatalogoV1> cursos;
}
