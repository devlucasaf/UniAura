package erp.uniaura.services.web.ouvidoria.pesquisarMinhasManifestacoes.v1;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class PesquisarMinhasManifestacoesWsV1 extends BaseWs {

    private final PesquisarMinhasManifestacoesServiceV1 pesquisarMinhasManifestacoesServiceV1;

    // --- ENDPOINT QUE LISTA AS MANIFESTAÇÕES DO USUÁRIO LOGADO ---
    @GetMapping
    @IsAuthenticated
    @Operation(summary = "Pesquisa as manifestações do usuário autenticado de forma paginada",
            description = "Lista só as manifestações em que o usuário autenticado é o autor, filtrando opcionalmente por status. "
                    + "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo).")
    public PesquisarMinhasManifestacoesRetornoV1 pesquisar(@LoggedInUserId String usuarioId,
                                                           @Valid @ParameterObject PesquisarMinhasManifestacoesParamsV1 params) {
        return pesquisarMinhasManifestacoesServiceV1.pesquisar(Long.valueOf(usuarioId), params);
    }
}
