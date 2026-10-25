package erp.uniaura.services.web.usuario.alterarUsuario.v1;

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
public class AlterarUsuarioWsV1 extends BaseWs {

    private final AlterarUsuarioServiceV1 alterarUsuarioServiceV1;

    // --- SÓ O ADMIN ALTERA USUÁRIOS ---
    @PostMapping
    @HasAuthority("ROLE_ADMIN")
    @Operation(summary = "Altera um usuário",
            description = "Atualiza os dados do usuário; a senha só muda se for informada. Erro 422 se o usuário não existir ou se o novo e-mail já estiver em uso.")
    public void alterar(@Valid @RequestBody AlterarUsuarioParamsV1 params) {
        alterarUsuarioServiceV1.alterar(params);
    }
}
