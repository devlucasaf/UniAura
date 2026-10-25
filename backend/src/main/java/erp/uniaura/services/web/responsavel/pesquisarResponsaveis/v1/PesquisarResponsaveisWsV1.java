package erp.uniaura.services.web.responsavel.pesquisarResponsaveis.v1;

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
public class PesquisarResponsaveisWsV1 extends BaseWs {

    private final PesquisarResponsaveisServiceV1 pesquisarResponsaveisServiceV1;

    // --- ENDPOINT QUE LISTA OS RESPONSÁVEIS DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Pesquisa responsáveis de forma paginada",
            description = "Lista os responsáveis, opcionalmente filtrando por parentesco. A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo). O retorno traz o total e a página pedida.")
    public PesquisarResponsaveisRetornoV1 pesquisar(@Valid @ParameterObject PesquisarResponsaveisParamsV1 params) {
        return pesquisarResponsaveisServiceV1.pesquisar(params);
    }
}
