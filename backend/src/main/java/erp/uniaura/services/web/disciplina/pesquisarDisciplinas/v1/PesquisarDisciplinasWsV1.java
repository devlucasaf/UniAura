package erp.uniaura.services.web.disciplina.pesquisarDisciplinas.v1;

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
public class PesquisarDisciplinasWsV1 extends BaseWs {

    private final PesquisarDisciplinasServiceV1 pesquisarDisciplinasServiceV1;

    // --- ENDPOINT QUE LISTA AS DISCIPLINAS DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa disciplinas de forma paginada",
            description = "Lista as disciplinas, opcionalmente filtrando pelo código exato. A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo). O retorno traz o total e a página pedida.")
    public PesquisarDisciplinasRetornoV1 pesquisar(@Valid @ParameterObject PesquisarDisciplinasParamsV1 params) {
        return pesquisarDisciplinasServiceV1.pesquisar(params);
    }
}
