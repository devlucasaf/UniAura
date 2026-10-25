package erp.uniaura.services.web.coordenacao.definirProfessorRegenteDaTurma.v1;

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
public class DefinirProfessorRegenteDaTurmaWsV1 extends BaseWs {

    private final DefinirProfessorRegenteDaTurmaServiceV1 definirProfessorRegenteDaTurmaServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN DEFINEM O REGENTE ---
    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Define ou remove o professor regente de uma turma",
            description = "Com 'professorUid' define o regente; sem ele, a turma fica sem regente. Erro 422 se a turma ou o professor não existirem.")
    public void definir(@Valid @RequestBody DefinirProfessorRegenteDaTurmaParamsV1 params) {
        definirProfessorRegenteDaTurmaServiceV1.definir(params);
    }
}
