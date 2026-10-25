package erp.uniaura.services.web.tcc.detalharTcc.v1;

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
public class DetalharTccWsV1 extends BaseWs {

    private final DetalharTccServiceV1 detalharTccServiceV1;

    @GetMapping
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN", "ROLE_PROFESSOR"})
    @Operation(summary = "Detalha um TCC",
            description = "Devolve os dados do TCC identificado pelo uid. Erro 422 se o TCC não existir.")
    public DetalharTccRetornoV1 detalhar(@Valid @ParameterObject DetalharTccParamsV1 params) {
        return detalharTccServiceV1.detalhar(params);
    }
}
