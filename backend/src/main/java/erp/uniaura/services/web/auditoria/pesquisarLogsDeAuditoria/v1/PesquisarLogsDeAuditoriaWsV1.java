package erp.uniaura.services.web.auditoria.pesquisarLogsDeAuditoria.v1;

import cloudsupport.security.HasAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarLogsDeAuditoriaWsV1 extends BaseWs {

    private final PesquisarLogsDeAuditoriaServiceV1 pesquisarLogsDeAuditoriaServiceV1;

    // --- SÓ O ADMIN CONSULTA A TRILHA DE AUDITORIA ---
    @GetMapping
    @HasAuthority("ROLE_ADMIN")
    @Operation(summary = "Pesquisa a trilha de auditoria por entidade ou usuário de forma paginada",
            description = "Filtra pela entidade ou, se ela não for informada, pelo usuário. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o usuário informado não existir.")
    public PesquisarLogsDeAuditoriaRetornoV1 pesquisar(@Valid @ParameterObject PesquisarLogsDeAuditoriaParamsV1 params) {
        return pesquisarLogsDeAuditoriaServiceV1.pesquisar(params);
    }
}
