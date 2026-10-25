package erp.uniaura.services.web.preMatricula.realizarPreMatricula.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealizarPreMatriculaRetornoV1 {

    @Schema(description = "Identificador público do aluno criado")
    private Long   alunoUid;
    private String nome;
    private String email;

    @Schema(description = "Registro acadêmico (RA) gerado, de 8 números")
    private String matriculaRA;
    private String curso;
}