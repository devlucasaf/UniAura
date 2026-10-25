package erp.uniaura.services.web.coordenacao.cancelarReuniaoDoColegiado.v1;

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
public class CancelarReuniaoDoColegiadoWsV1 extends BaseWs {

    private final CancelarReuniaoDoColegiadoServiceV1 cancelarReuniaoDoColegiadoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN CANCELAM REUNIÕES ---
    @PostMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Cancela uma reunião do colegiado",
            description = "Passa a reunião AGENDADA para CANCELADA e guarda o motivo. "
                    + "Erro 422 se a reunião não existir ou se já foi realizada ou cancelada.")
    public void cancelar(@Valid @RequestBody CancelarReuniaoDoColegiadoParamsV1 params) {
        cancelarReuniaoDoColegiadoServiceV1.cancelar(params);
    }
}
