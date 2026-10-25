package erp.uniaura.services.web.biblioteca.cancelarMulta.v1;

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
public class CancelarMultaWsV1 extends BaseWs {

    private final CancelarMultaServiceV1 cancelarMultaServiceV1;

    // --- ENDPOINT QUE CANCELA UMA MULTA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN"})
    @Operation(summary = "Cancela uma multa",
            description = "Só o admin cancela multa. Erro 422 se a multa não existir ou se já tiver sido paga.")
    public void cancelar(@Valid @RequestBody CancelarMultaParamsV1 params) {
        cancelarMultaServiceV1.cancelar(params);
    }
}
