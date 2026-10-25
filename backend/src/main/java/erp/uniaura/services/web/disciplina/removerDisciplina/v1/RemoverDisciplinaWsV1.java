package erp.uniaura.services.web.disciplina.removerDisciplina.v1;

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
public class RemoverDisciplinaWsV1 extends BaseWs {

    private final RemoverDisciplinaServiceV1 removerDisciplinaServiceV1;

    // --- ENDPOINT QUE REMOVE UMA DISCIPLINA ---
    @PostMapping
    @HasAuthority("ROLE_ADMIN")
    @Operation(summary = "Remove uma disciplina",
            description = "Remove a disciplina identificada pelo uid. Erro 422 se a disciplina não existir.")
    public void remover(@Valid @RequestBody RemoverDisciplinaParamsV1 params) {
        removerDisciplinaServiceV1.remover(params);
    }
}
