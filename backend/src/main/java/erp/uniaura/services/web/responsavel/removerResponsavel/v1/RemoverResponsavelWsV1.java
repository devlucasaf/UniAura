package erp.uniaura.services.web.responsavel.removerResponsavel.v1;

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
public class RemoverResponsavelWsV1 extends BaseWs {

    private final RemoverResponsavelServiceV1 removerResponsavelServiceV1;

    // --- ENDPOINT QUE REMOVE UM RESPONSÁVEL ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Remove um responsável",
            description = "Remove o responsável identificado pelo uid. O usuário vinculado não é removido. Erro 422 se o responsável não existir.")
    public void remover(@Valid @RequestBody RemoverResponsavelParamsV1 params) {
        removerResponsavelServiceV1.remover(params);
    }
}
