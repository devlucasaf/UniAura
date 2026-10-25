package erp.uniaura.services.web.disciplina.adicionarPrerequisitoDaDisciplina.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AdicionarPrerequisitoDaDisciplinaWsV1 extends BaseWs {

    private final AdicionarPrerequisitoDaDisciplinaServiceV1 adicionarPrerequisitoDaDisciplinaServiceV1;

    // --- ENDPOINT QUE ADICIONA UM PRÉ-REQUISITO A UMA DISCIPLINA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    @Operation(summary = "Adiciona um pré-requisito a uma disciplina",
            description = "Vincula 'prerequisitoUid' como pré-requisito de 'disciplinaUid' e devolve o uid do vínculo. "
                    + "Erro 422 se alguma disciplina não existir, se forem a mesma ou se o vínculo já existir.")
    public Uid adicionar(@Valid @RequestBody AdicionarPrerequisitoDaDisciplinaParamsV1 params) {
        return adicionarPrerequisitoDaDisciplinaServiceV1.adicionar(params);
    }
}
