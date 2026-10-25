package erp.uniaura.services.web.autenticacao.solicitarRecuperacaoDeSenha.v1;

import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class SolicitarRecuperacaoDeSenhaWsV1 extends BaseWs {

    private final SolicitarRecuperacaoDeSenhaServiceV1 solicitarRecuperacaoDeSenhaServiceV1;

    // --- ENDPOINT QUE GERA O CÓDIGO DE RECUPERAÇÃO DE SENHA ---
    @PostMapping
    @Operation(summary = "Solicita o código de recuperação de senha",
            description = "Confere e-mail, CPF e data de nascimento e gera um código de 6 números válido por 10 minutos (sem envio de e-mail). "
                    + "Erro 422 se os dados não conferirem ou se houver excesso de tentativas.")
    public SolicitarRecuperacaoDeSenhaRetornoV1 solicitar(@Valid @RequestBody SolicitarRecuperacaoDeSenhaParamsV1 params) {
        return solicitarRecuperacaoDeSenhaServiceV1.solicitar(params);
    }
}
