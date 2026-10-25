package erp.uniaura.services.web.avaliacaoDocente.consultarMediaDoProfessor.v1;

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
public class ConsultarMediaDoProfessorWsV1 extends BaseWs {

    private final ConsultarMediaDoProfessorServiceV1 consultarMediaDoProfessorServiceV1;

    // --- ENDPOINT QUE RETORNA AS MÉDIAS CONSOLIDADAS DO PROFESSOR ---
    @GetMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Consulta a média consolidada de um professor",
            description = "Considera todas as turmas e disciplinas do professor e devolve a quantidade de avaliações e as médias de didática, pontualidade, disponibilidade e geral. Erro 422 se o professor não existir.")
    public ConsultarMediaDoProfessorRetornoV1 consultar(@Valid @ParameterObject ConsultarMediaDoProfessorParamsV1 params) {
        return consultarMediaDoProfessorServiceV1.consultar(params);
    }
}
