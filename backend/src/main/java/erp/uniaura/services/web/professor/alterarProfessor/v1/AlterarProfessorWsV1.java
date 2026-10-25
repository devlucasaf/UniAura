package erp.uniaura.services.web.professor.alterarProfessor.v1;

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
public class AlterarProfessorWsV1 extends BaseWs {

    private final AlterarProfessorServiceV1 alterarProfessorServiceV1;

    // --- ENDPOINT QUE ALTERA UM PROFESSOR ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Altera um professor",
            description = "Atualiza os dados do professor e do usuário vinculado, identificado pelo uid. A senha não é alterada aqui. "
                    + "Erro 422 se o professor não existir. Sem 'ativo', a situação atual é mantida.")
    public void alterar(@Valid @RequestBody AlterarProfessorParamsV1 params) {
        alterarProfessorServiceV1.alterar(params);
    }
}
