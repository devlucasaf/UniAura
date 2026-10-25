package erp.uniaura.services.web.comunicado.pesquisarComunicados.v1;

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
public class PesquisarComunicadosWsV1 extends BaseWs {

    private final PesquisarComunicadosServiceV1 pesquisarComunicadosServiceV1;

    // --- ENDPOINT QUE LISTA OS COMUNICADOS DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa comunicados de forma paginada",
            description = "Lista os comunicados, opcionalmente filtrando por público-alvo. A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo), por padrão os mais recentes primeiro. "
                    + "Para o feed de quem está logado, use pesquisarMural.")
    public PesquisarComunicadosRetornoV1 pesquisar(@Valid @ParameterObject PesquisarComunicadosParamsV1 params) {
        return pesquisarComunicadosServiceV1.pesquisar(params);
    }
}
