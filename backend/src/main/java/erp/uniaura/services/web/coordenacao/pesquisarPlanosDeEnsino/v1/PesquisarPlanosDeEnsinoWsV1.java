package erp.uniaura.services.web.coordenacao.pesquisarPlanosDeEnsino.v1;

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
public class PesquisarPlanosDeEnsinoWsV1 extends BaseWs {

    private final PesquisarPlanosDeEnsinoServiceV1 pesquisarPlanosDeEnsinoServiceV1;

    // --- SÓ A COORDENAÇÃO E O ADMIN VEEM A FILA DE PLANOS ---
    @GetMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Pesquisa planos de ensino por status, curso e período letivo de forma paginada",
            description = "Lista os planos sem o conteúdo e sem o histórico. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o curso informado não existir.")
    public PesquisarPlanosDeEnsinoRetornoV1 pesquisar(@Valid @ParameterObject PesquisarPlanosDeEnsinoParamsV1 params) {
        return pesquisarPlanosDeEnsinoServiceV1.pesquisar(params);
    }
}
