package erp.uniaura.services.web.turma.alterarTurma.v1;

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
public class AlterarTurmaWsV1 extends BaseWs {

    private final AlterarTurmaServiceV1 alterarTurmaServiceV1;

    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Altera uma turma",
            description = "Atualiza os dados da turma identificada pelo uid, podendo trocá-la de curso. Sem 'professorRegenteId', a turma fica sem regente. "
                    + "Erro 422 se a turma, o curso ou o professor não existirem, ou se o novo código já pertencer a outra turma. Sem 'ativa', a situação atual é mantida.")
    public void alterar(@Valid @RequestBody AlterarTurmaParamsV1 params) {
        alterarTurmaServiceV1.alterar(params);
    }
}
