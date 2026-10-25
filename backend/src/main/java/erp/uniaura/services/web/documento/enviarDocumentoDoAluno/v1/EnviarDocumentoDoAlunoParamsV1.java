package erp.uniaura.services.web.documento.enviarDocumentoDoAluno.v1;

import erp.uniaura.persistence.entities.TipoDocumento;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnviarDocumentoDoAlunoParamsV1 {

    @Schema(description = "Identificador público do aluno dono do documento. Obrigatório para a equipe; ignorado quando quem envia é o próprio aluno")
    private Long alunoUid;

    @Schema(description = "Tipo do documento")
    @NotNull(message = "O tipo do documento é obrigatório.")
    private TipoDocumento tipo;
}
