package erp.uniaura.services.web.calendario.alterarEventoAcademico.v1;

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
public class AlterarEventoAcademicoWsV1 extends BaseWs {

    private final AlterarEventoAcademicoServiceV1 alterarEventoAcademicoServiceV1;

    // --- ENDPOINT QUE ALTERA UM EVENTO DO CALENDÁRIO ACADÊMICO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Altera um evento do calendário acadêmico",
            description = "Atualiza os dados do evento identificado pelo uid. Sem 'publico', o evento deixa de ser público. Erro 422 se o evento não existir.")
    public void alterar(@Valid @RequestBody AlterarEventoAcademicoParamsV1 params) {
        alterarEventoAcademicoServiceV1.alterar(params);
    }
}
