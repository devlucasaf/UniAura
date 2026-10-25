package erp.uniaura.services.web.usuario.cadastrarUsuario.v1;

import cloudsupport.security.HasAuthority;
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
public class CadastrarUsuarioWsV1 extends BaseWs {

    private final CadastrarUsuarioServiceV1 cadastrarUsuarioServiceV1;

    // --- SÓ O ADMIN CRIA USUÁRIOS; SUBSTITUI TAMBÉM O ANTIGO /auth/register ---
    @PostMapping
    @HasAuthority("ROLE_ADMIN")
    @Operation(summary = "Cadastra um usuário",
            description = "Cria o usuário e devolve o uid gerado. Erro 422 se o e-mail já estiver em uso.")
    public Uid cadastrar(@Valid @RequestBody CadastrarUsuarioParamsV1 params) {
        return cadastrarUsuarioServiceV1.cadastrar(params);
    }
}
