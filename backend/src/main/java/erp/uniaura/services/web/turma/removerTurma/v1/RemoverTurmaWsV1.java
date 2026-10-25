package erp.uniaura.services.web.turma.removerTurma.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RemoverTurmaWsV1 extends BaseWs {

    private final RemoverTurmaServiceV1 removerTurmaServiceV1;

    @PostMapping
    @HasAuthority("ROLE_ADMIN")
    @Operation(summary = "Remove uma turma",
            description = "Remove a turma identificada pelo uid. Erro 422 se a turma não existir.")
    public void remover(@Valid @RequestBody RemoverTurmaParamsV1 params) {
        removerTurmaServiceV1.remover(params);
    }
}
