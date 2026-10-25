package erp.uniaura.services.web.nota.consultarBoletim.v1;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultarBoletimRetornoV1 {

    private String                                      alunoNome;
    private String                                      alunoMatriculaRA;
    private String                                      periodoLetivo;
    private List<ConsultarBoletimDisciplinaRetornoV1>   disciplinas;
}
