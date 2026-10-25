package erp.uniaura.services.web.calendario.detalharEventoAcademico.v1;

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
public class DetalharEventoAcademicoWsV1 extends BaseWs {

    private final DetalharEventoAcademicoServiceV1 detalharEventoAcademicoServiceV1;

    // --- ENDPOINT QUE DETALHA UM EVENTO DO CALENDÁRIO ACADÊMICO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Detalha um evento do calendário acadêmico",
            description = "Devolve os dados completos do evento identificado pelo uid. Erro 422 se o evento não existir.")
    public DetalharEventoAcademicoRetornoV1 detalhar(@Valid @ParameterObject DetalharEventoAcademicoParamsV1 params) {
        return detalharEventoAcademicoServiceV1.detalhar(params);
    }
}
