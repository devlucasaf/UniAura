package erp.uniaura.services.web.coordenacao.pesquisarReunioesDoColegiado.v1;

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
public class PesquisarReunioesDoColegiadoWsV1 extends BaseWs {

    private final PesquisarReunioesDoColegiadoServiceV1 pesquisarReunioesDoColegiadoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN LISTAM AS REUNIÕES ---
    @GetMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Pesquisa reuniões do colegiado por curso e status de forma paginada",
            description = "Lista as reuniões sem pauta, deliberações e participantes. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o curso informado não existir.")
    public PesquisarReunioesDoColegiadoRetornoV1 pesquisar(@Valid @ParameterObject PesquisarReunioesDoColegiadoParamsV1 params) {
        return pesquisarReunioesDoColegiadoServiceV1.pesquisar(params);
    }
}
