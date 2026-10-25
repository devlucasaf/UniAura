package erp.uniaura.services.web.frequencia.pesquisarFrequenciasDaAula.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PesquisarFrequenciasDaAulaFrequenciaRetornoV1 {

    @Schema(description = "Identificador público do registro de frequência")
    private Long    uid;

    @Schema(description = "Identificador público do aluno")
    private Long    alunoUid;
    private String  alunoNome;
    private String  alunoMatriculaRA;
    private Boolean presente;
    private String  justificativa;
}
