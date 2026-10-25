package erp.uniaura.services.web.calendario.removerEventoAcademico.v1;

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
public class RemoverEventoAcademicoWsV1 extends BaseWs {

    private final RemoverEventoAcademicoServiceV1 removerEventoAcademicoServiceV1;

    // --- ENDPOINT QUE REMOVE UM EVENTO DO CALENDÁRIO ACADÊMICO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Remove um evento do calendário acadêmico",
            description = "Remove o evento identificado pelo uid. Erro 422 se o evento não existir.")
    public void remover(@Valid @RequestBody RemoverEventoAcademicoParamsV1 params) {
        removerEventoAcademicoServiceV1.remover(params);
    }
}
