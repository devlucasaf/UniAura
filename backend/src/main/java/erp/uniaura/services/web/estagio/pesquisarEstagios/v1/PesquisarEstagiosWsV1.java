package erp.uniaura.services.web.estagio.pesquisarEstagios.v1;

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
public class PesquisarEstagiosWsV1 extends BaseWs {

    private final PesquisarEstagiosServiceV1 pesquisarEstagiosServiceV1;

    // --- ENDPOINT QUE LISTA OS ESTÁGIOS DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa estágios de forma paginada",
            description = "Lista os estágios, filtrando opcionalmente por aluno e status. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o aluno do filtro não existir.")
    public PesquisarEstagiosRetornoV1 pesquisar(@Valid @ParameterObject PesquisarEstagiosParamsV1 params) {
        return pesquisarEstagiosServiceV1.pesquisar(params);
    }
}
