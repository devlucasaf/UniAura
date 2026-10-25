package erp.uniaura.services.web.processo.pesquisarProcessos.v1;

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
public class PesquisarProcessosWsV1 extends BaseWs {

    private final PesquisarProcessosServiceV1 pesquisarProcessosServiceV1;

    // --- ENDPOINT QUE LISTA A FILA DE PROCESSOS DA SECRETARIA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Pesquisa a fila de processos da secretaria de forma paginada",
            description = "Lista os processos de todos os alunos, filtrando opcionalmente por status e tipo. A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo).")
    public PesquisarProcessosRetornoV1 pesquisar(@Valid @ParameterObject PesquisarProcessosParamsV1 params) {
        return pesquisarProcessosServiceV1.pesquisar(params);
    }
}
