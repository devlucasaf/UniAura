package erp.uniaura.services.web.certificado.emitirCertificadoDeConclusao.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmitirCertificadoDeConclusaoParamsV1 {

    @Schema(description = "Identificador público do aluno")
    @NotNull(message = "O uid do aluno é obrigatório.")
    private Long alunoUid;
}
