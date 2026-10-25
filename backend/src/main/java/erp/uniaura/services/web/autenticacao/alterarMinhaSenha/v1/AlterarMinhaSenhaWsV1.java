package erp.uniaura.services.web.autenticacao.alterarMinhaSenha.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AlterarMinhaSenhaWsV1 extends BaseWs {

    private final AlterarMinhaSenhaServiceV1 alterarMinhaSenhaServiceV1;

    // --- QUALQUER USUÁRIO LOGADO TROCA A PRÓPRIA SENHA ---
    @PostMapping
    @IsAuthenticated
    @Operation(summary = "Altera a senha do usuário autenticado",
            description = "Exige a senha atual. Erro 422 se a senha atual estiver incorreta ou se a nova for igual à atual.")
    public void alterar(@LoggedInUserId String usuarioId, @Valid @RequestBody AlterarMinhaSenhaParamsV1 params) {
        alterarMinhaSenhaServiceV1.alterar(Long.valueOf(usuarioId), params);
    }
}
