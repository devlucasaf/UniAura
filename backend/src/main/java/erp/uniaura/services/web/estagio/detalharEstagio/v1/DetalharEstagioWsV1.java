package erp.uniaura.services.web.estagio.detalharEstagio.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class DetalharEstagioWsV1 extends BaseWs {

    private final DetalharEstagioServiceV1 detalharEstagioServiceV1;

    // --- ENDPOINT QUE DETALHA UM ESTÁGIO PELO UID ---
    @GetMapping
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN", "ROLE_PROFESSOR"})
    @Operation(summary = "Detalha um estágio",
            description = "Devolve os dados do estágio identificado pelo uid. Erro 422 se o estágio não existir.")
    public DetalharEstagioRetornoV1 detalhar(@Valid @ParameterObject DetalharEstagioParamsV1 params) {
        return detalharEstagioServiceV1.detalhar(params);
    }
}
