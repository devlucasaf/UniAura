package erp.uniaura.services.web.financeiro.removerMensalidade.v1;

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
public class RemoverMensalidadeWsV1 extends BaseWs {

    private final RemoverMensalidadeServiceV1 removerMensalidadeServiceV1;

    // --- SÓ O ADMIN APAGA MENSALIDADES ---
    @PostMapping
    @HasAuthority("ROLE_ADMIN")
    @Operation(summary = "Remove uma mensalidade",
            description = "Apaga a mensalidade ainda não paga. Erro 422 se a mensalidade não existir ou se já estiver paga.")
    public void remover(@Valid @RequestBody RemoverMensalidadeParamsV1 params) {
        removerMensalidadeServiceV1.remover(params);
    }
}
