package erp.uniaura.services.web.responsavel.alterarResponsavel.v1;

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
public class AlterarResponsavelWsV1 extends BaseWs {

    private final AlterarResponsavelServiceV1 alterarResponsavelServiceV1;

    // --- ENDPOINT QUE ALTERA UM RESPONSÁVEL ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Altera um responsável",
            description = "Atualiza os dados do responsável e do usuário vinculado, identificado pelo uid. A senha não é alterada aqui. Erro 422 se o responsável não existir.")
    public void alterar(@Valid @RequestBody AlterarResponsavelParamsV1 params) {
        alterarResponsavelServiceV1.alterar(params);
    }
}
