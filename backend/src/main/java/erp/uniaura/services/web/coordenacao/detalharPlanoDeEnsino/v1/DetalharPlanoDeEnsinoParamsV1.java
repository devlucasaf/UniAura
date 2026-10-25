package erp.uniaura.services.web.coordenacao.detalharPlanoDeEnsino.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharPlanoDeEnsinoParamsV1 {

    @Schema(description = "Identificador público do plano; informe este ou o uid do vínculo entre turma e disciplina")
    private Long uid;

    @Schema(description = "Identificador público do vínculo entre turma e disciplina; informe este ou o uid do plano")
    private Long turmaDisciplinaUid;
}
