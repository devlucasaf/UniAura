package erp.uniaura.services.web.autenticacao.redefinirSenha.v1;

import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class RedefinirSenhaWsV1 extends BaseWs {

    private final RedefinirSenhaServiceV1 redefinirSenhaServiceV1;

    // --- ENDPOINT QUE REDEFINE A SENHA COM O CÓDIGO DE RECUPERAÇÃO ---
    @PostMapping
    @Operation(summary = "Redefine a senha com o código de recuperação",
            description = "Troca a senha se o código estiver correto e dentro do prazo. "
                    + "Erro 422 se o código for inválido, expirado ou incorreto, ou após 5 tentativas erradas.")
    public void redefinir(@Valid @RequestBody RedefinirSenhaParamsV1 params) {
        redefinirSenhaServiceV1.redefinir(params);
    }
}
