package erp.uniaura.services.web.disciplina.removerPrerequisitoDaDisciplina.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RemoverPrerequisitoDaDisciplinaWsV1 extends BaseWs {

    private final RemoverPrerequisitoDaDisciplinaServiceV1 removerPrerequisitoDaDisciplinaServiceV1;

    // --- ENDPOINT QUE REMOVE UM PRÉ-REQUISITO DE UMA DISCIPLINA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Remove um pré-requisito de uma disciplina",
            description = "Desfaz o vínculo entre 'disciplinaUid' e o pré-requisito 'prerequisitoUid'. Erro 422 se o vínculo não existir.")
    public void remover(@Valid @RequestBody RemoverPrerequisitoDaDisciplinaParamsV1 params) {
        removerPrerequisitoDaDisciplinaServiceV1.remover(params);
    }
}
