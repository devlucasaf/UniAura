package erp.uniaura.services.web.frequencia.consultarPercentualDePresenca.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class ConsultarPercentualDePresencaWsV1 extends BaseWs {

    private final ConsultarPercentualDePresencaServiceV1 consultarPercentualDePresencaServiceV1;

    // --- ENDPOINT QUE CONSULTA O PERCENTUAL DE PRESENÇA DO ALUNO NA DISCIPLINA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Consulta o percentual de presença do aluno em uma disciplina",
            description = "Devolve o total de aulas, as presenças, as faltas e o percentual. Erro 422 se o aluno ou a disciplina não existirem, ou se o aluno não tiver frequência registrada na disciplina.")
    public ConsultarPercentualDePresencaRetornoV1 consultar(@Valid @ParameterObject ConsultarPercentualDePresencaParamsV1 params) {
        return consultarPercentualDePresencaServiceV1.consultar(params);
    }
}
