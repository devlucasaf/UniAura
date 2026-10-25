package erp.uniaura.services.web.financeiro.registrarPagamentoDaMensalidade.v1;

import cloudsupport.security.HasAnyAuthority;
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
public class RegistrarPagamentoDaMensalidadeWsV1 extends BaseWs {

    private final RegistrarPagamentoDaMensalidadeServiceV1 registrarPagamentoDaMensalidadeServiceV1;

    // --- O PRÓPRIO ALUNO OU A EQUIPE FINANCEIRA QUITAM A MENSALIDADE ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_SECRETARIA", "ROLE_ALUNO"})
    @Operation(summary = "Registra o pagamento de uma mensalidade",
            description = "Quita a mensalidade, somando multa de 2% mais 0,033% ao dia quando atrasada, e grava só os 4 últimos dígitos do cartão. "
                    + "Erro 422 se a mensalidade não existir, se for de outro aluno, se já estiver paga ou cancelada ou se faltarem os dados do cartão.")
    public void registrar(@LoggedInUserId String usuarioId, @Valid @RequestBody RegistrarPagamentoDaMensalidadeParamsV1 params) {
        registrarPagamentoDaMensalidadeServiceV1.registrar(Long.valueOf(usuarioId), params);
    }
}
