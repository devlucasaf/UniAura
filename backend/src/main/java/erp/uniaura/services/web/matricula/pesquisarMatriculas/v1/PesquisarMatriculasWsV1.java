package erp.uniaura.services.web.matricula.pesquisarMatriculas.v1;

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
public class PesquisarMatriculasWsV1 extends BaseWs {

    private final PesquisarMatriculasServiceV1 pesquisarMatriculasServiceV1;

    // --- ENDPOINT QUE LISTA AS MATRÍCULAS DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Pesquisa matrículas de forma paginada",
            description = "Lista as matrículas, filtrando opcionalmente por aluno (histórico do aluno) e status. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se o aluno do filtro não existir.")
    public PesquisarMatriculasRetornoV1 pesquisar(@Valid @ParameterObject PesquisarMatriculasParamsV1 params) {
        return pesquisarMatriculasServiceV1.pesquisar(params);
    }
}
