package erp.uniaura.services.web.ouvidoria.pesquisarManifestacoes.v1;

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
public class PesquisarManifestacoesWsV1 extends BaseWs {

    private final PesquisarManifestacoesServiceV1 pesquisarManifestacoesServiceV1;

    // --- ENDPOINT QUE LISTA A FILA DE MANIFESTAÇÕES DA OUVIDORIA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Pesquisa a fila de manifestações da ouvidoria de forma paginada",
            description = "Lista as manifestações de todos os autores, filtrando opcionalmente por status e tipo. O nome do autor não é devolvido nas manifestações anônimas. "
                    + "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo).")
    public PesquisarManifestacoesRetornoV1 pesquisar(@Valid @ParameterObject PesquisarManifestacoesParamsV1 params) {
        return pesquisarManifestacoesServiceV1.pesquisar(params);
    }
}
