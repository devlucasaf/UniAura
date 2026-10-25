package erp.uniaura.services.web.funcionario.pesquisarFuncionarios.v1;

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
public class PesquisarFuncionariosWsV1 extends BaseWs {

    private final PesquisarFuncionariosServiceV1 pesquisarFuncionariosServiceV1;

    // --- ENDPOINT QUE LISTA OS FUNCIONÁRIOS DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Pesquisa funcionários de forma paginada",
            description = "Lista os funcionários, opcionalmente filtrando por cargo. A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo). O retorno traz o total e a página pedida.")
    public PesquisarFuncionariosRetornoV1 pesquisar(@Valid @ParameterObject PesquisarFuncionariosParamsV1 params) {
        return pesquisarFuncionariosServiceV1.pesquisar(params);
    }
}
