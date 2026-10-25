package erp.uniaura.services.web.disciplina.alterarDisciplina.v1;

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
public class AlterarDisciplinaWsV1 extends BaseWs {

    private final AlterarDisciplinaServiceV1 alterarDisciplinaServiceV1;

    // --- ENDPOINT QUE ALTERA UMA DISCIPLINA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Altera uma disciplina",
            description = "Atualiza os dados da disciplina identificada pelo uid, podendo trocá-la de curso. "
                    + "Erro 422 se a disciplina ou o curso não existirem, ou se o novo código já pertencer a outra disciplina. "
                    + "Sem 'ativo', a situação atual é mantida.")
    public void alterar(@Valid @RequestBody AlterarDisciplinaParamsV1 params) {
        alterarDisciplinaServiceV1.alterar(params);
    }
}
