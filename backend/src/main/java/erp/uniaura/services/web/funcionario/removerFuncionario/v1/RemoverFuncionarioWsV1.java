package erp.uniaura.services.web.funcionario.removerFuncionario.v1;

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
public class RemoverFuncionarioWsV1 extends BaseWs {

    private final RemoverFuncionarioServiceV1 removerFuncionarioServiceV1;

    // --- ENDPOINT QUE REMOVE UM FUNCIONÁRIO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Remove um funcionário",
            description = "Remove o funcionário identificado pelo uid. O usuário vinculado não é removido. Erro 422 se o funcionário não existir.")
    public void remover(@Valid @RequestBody RemoverFuncionarioParamsV1 params) {
        removerFuncionarioServiceV1.remover(params);
    }
}
