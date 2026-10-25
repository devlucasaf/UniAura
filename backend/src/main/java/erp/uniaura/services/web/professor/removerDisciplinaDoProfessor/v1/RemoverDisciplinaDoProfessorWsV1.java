package erp.uniaura.services.web.professor.removerDisciplinaDoProfessor.v1;

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
public class RemoverDisciplinaDoProfessorWsV1 extends BaseWs {

    private final RemoverDisciplinaDoProfessorServiceV1 removerDisciplinaDoProfessorServiceV1;

    // --- ENDPOINT QUE REMOVE A HABILITAÇÃO DO PROFESSOR PARA A DISCIPLINA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Remove a habilitação de um professor para uma disciplina",
            description = "Desfaz o vínculo entre o professor e a disciplina. Erro 422 se professor ou disciplina não existirem ou se o vínculo não existir.")
    public void remover(@Valid @RequestBody RemoverDisciplinaDoProfessorParamsV1 params) {
        removerDisciplinaDoProfessorServiceV1.remover(params);
    }
}
