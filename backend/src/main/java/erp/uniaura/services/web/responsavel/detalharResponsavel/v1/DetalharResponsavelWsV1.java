package erp.uniaura.services.web.responsavel.detalharResponsavel.v1;

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
public class DetalharResponsavelWsV1 extends BaseWs {

    private final DetalharResponsavelServiceV1 detalharResponsavelServiceV1;

    // --- ENDPOINT QUE DETALHA UM RESPONSÁVEL PELO UID ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Detalha um responsável",
            description = "Devolve os dados completos do responsável (incluindo os do usuário vinculado) identificado pelo uid. Erro 422 se o responsável não existir.")
    public DetalharResponsavelRetornoV1 detalhar(@Valid @ParameterObject DetalharResponsavelParamsV1 params) {
        return detalharResponsavelServiceV1.detalhar(params);
    }
}
