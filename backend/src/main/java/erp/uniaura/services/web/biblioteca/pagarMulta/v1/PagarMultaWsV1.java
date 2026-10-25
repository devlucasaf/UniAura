package erp.uniaura.services.web.biblioteca.pagarMulta.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class PagarMultaWsV1 extends BaseWs {

    private final PagarMultaServiceV1 pagarMultaServiceV1;

    // --- ENDPOINT QUE REGISTRA O PAGAMENTO DE UMA MULTA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN", "ROLE_FINANCEIRO"})
    @Operation(summary = "Registra o pagamento de uma multa",
            description = "Marca a multa como PAGA e grava a data do pagamento. Erro 422 se a multa não existir ou não estiver PENDENTE.")
    public void pagar(@Valid @RequestBody PagarMultaParamsV1 params) {
        pagarMultaServiceV1.pagar(params);
    }
}
