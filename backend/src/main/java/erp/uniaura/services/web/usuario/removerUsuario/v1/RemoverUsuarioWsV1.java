package erp.uniaura.services.web.usuario.removerUsuario.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RemoverUsuarioWsV1 extends BaseWs {

    private final RemoverUsuarioServiceV1 removerUsuarioServiceV1;

    // --- SÓ O ADMIN APAGA USUÁRIOS ---
    @PostMapping
    @HasAuthority("ROLE_ADMIN")
    @Operation(summary = "Remove um usuário",
            description = "Apaga o usuário. Erro 422 se o usuário não existir; o banco recusa se ele ainda estiver vinculado a outros cadastros.")
    public void remover(@Valid @RequestBody RemoverUsuarioParamsV1 params) {
        removerUsuarioServiceV1.remover(params);
    }
}
