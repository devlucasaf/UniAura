package erp.uniaura.services.web.autenticacao.renovarToken.v1;

import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RenovarTokenWsV1 extends BaseWs {

    private final RenovarTokenServiceV1 renovarTokenServiceV1;

    // --- ENDPOINT QUE RENOVA O TOKEN DE ACESSO PELO REFRESH TOKEN ---
    @PostMapping
    @Operation(summary = "Renova o token de acesso a partir do refresh token",
            description = "Devolve um novo par de tokens. Erro 422 se o refresh token for inválido ou se o usuário estiver inativo.")
    public RenovarTokenRetornoV1 renovar(@Valid @RequestBody RenovarTokenParamsV1 params) {
        return renovarTokenServiceV1.renovar(params);
    }
}
