package erp.uniaura.services.web.turma.detalharTurma.v1;

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
public class DetalharTurmaWsV1 extends BaseWs {

    private final DetalharTurmaServiceV1 detalharTurmaServiceV1;

    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Detalha uma turma",
            description = "Devolve os dados completos da turma identificada pelo uid. Erro 422 se a turma não existir.")
    public DetalharTurmaRetornoV1 detalhar(@Valid @ParameterObject DetalharTurmaParamsV1 params) {
        return detalharTurmaServiceV1.detalhar(params);
    }
}
