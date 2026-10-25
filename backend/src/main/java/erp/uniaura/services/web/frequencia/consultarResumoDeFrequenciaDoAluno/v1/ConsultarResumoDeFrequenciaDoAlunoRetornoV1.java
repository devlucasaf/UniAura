package erp.uniaura.services.web.frequencia.consultarResumoDeFrequenciaDoAluno.v1;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultarResumoDeFrequenciaDoAlunoRetornoV1 {

    private String                                                          alunoNome;
    private String                                                          alunoMatriculaRA;
    private List<ConsultarResumoDeFrequenciaDoAlunoDisciplinaRetornoV1>     disciplinas;
}
