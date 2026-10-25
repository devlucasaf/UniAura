package erp.uniaura.services.web.autenticacao.autenticarUsuario.v1;

import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AutenticarUsuarioWsV1 extends BaseWs {

    private final AutenticarUsuarioServiceV1 autenticarUsuarioServiceV1;

    // --- ENDPOINT QUE AUTENTICA O USUÁRIO E RETORNA OS TOKENS ---
    @PostMapping
    @Operation(summary = "Autentica o usuário por e-mail e senha",
            description = "Devolve o token de acesso, o refresh token e os dados básicos do usuário. "
                    + "Erro 422 se as credenciais forem inválidas ou se o e-mail estiver bloqueado por excesso de tentativas.")
    public AutenticarUsuarioRetornoV1 autenticar(@Valid @RequestBody AutenticarUsuarioParamsV1 params) {
        return autenticarUsuarioServiceV1.autenticar(params);
    }
}
