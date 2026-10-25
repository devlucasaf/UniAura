package erp.uniaura.services.web.biblioteca.pesquisarMultas.v1;

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
public class PesquisarMultasWsV1 extends BaseWs {

    private final PesquisarMultasServiceV1 pesquisarMultasServiceV1;

    // --- ENDPOINT QUE LISTA AS MULTAS POR STATUS DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN", "ROLE_FINANCEIRO"})
    @Operation(summary = "Pesquisa multas da biblioteca por status de forma paginada",
            description = "Lista as multas no status pedido (PENDENTE por padrão). A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo).")
    public PesquisarMultasRetornoV1 pesquisar(@Valid @ParameterObject PesquisarMultasParamsV1 params) {
        return pesquisarMultasServiceV1.pesquisar(params);
    }
}
