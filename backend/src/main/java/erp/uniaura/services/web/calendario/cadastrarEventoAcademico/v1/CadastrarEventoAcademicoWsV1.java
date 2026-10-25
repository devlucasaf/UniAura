package erp.uniaura.services.web.calendario.cadastrarEventoAcademico.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.Uid;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class CadastrarEventoAcademicoWsV1 extends BaseWs {

    private final CadastrarEventoAcademicoServiceV1 cadastrarEventoAcademicoServiceV1;

    // --- ENDPOINT QUE CADASTRA UM EVENTO NO CALENDÁRIO ACADÊMICO ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Cadastra um evento no calendário acadêmico",
            description = "Cria o evento e devolve o uid gerado. Sem 'publico', o evento não aparece no calendário do site.")
    public Uid cadastrar(@Valid @RequestBody CadastrarEventoAcademicoParamsV1 params) {
        return cadastrarEventoAcademicoServiceV1.cadastrar(params);
    }
}
