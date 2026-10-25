package erp.uniaura.services.web.aluno.detalharAluno.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharAlunoParamsV1 {

    @Schema(description = "Identificador público do aluno; informe este ou a matrícula")
    private Long uid;

    @Schema(description = "Registro acadêmico (RA) do aluno; informe este ou o uid")
    private String matriculaRA;
}
