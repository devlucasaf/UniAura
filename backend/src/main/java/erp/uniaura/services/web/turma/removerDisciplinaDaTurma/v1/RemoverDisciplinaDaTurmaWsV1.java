package erp.uniaura.services.web.turma.removerDisciplinaDaTurma.v1;

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
public class RemoverDisciplinaDaTurmaWsV1 extends BaseWs {

    private final RemoverDisciplinaDaTurmaServiceV1 removerDisciplinaDaTurmaServiceV1;

    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Remove uma disciplina de uma turma",
            description = "Desfaz o vínculo entre a turma e a disciplina. Erro 422 se a disciplina não estiver vinculada à turma.")
    public void remover(@Valid @RequestBody RemoverDisciplinaDaTurmaParamsV1 params) {
        removerDisciplinaDaTurmaServiceV1.remover(params);
    }
}
