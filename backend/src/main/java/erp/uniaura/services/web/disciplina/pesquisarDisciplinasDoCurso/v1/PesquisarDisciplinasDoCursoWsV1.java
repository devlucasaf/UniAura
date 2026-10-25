package erp.uniaura.services.web.disciplina.pesquisarDisciplinasDoCurso.v1;

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
public class PesquisarDisciplinasDoCursoWsV1 extends BaseWs {

    private final PesquisarDisciplinasDoCursoServiceV1 pesquisarDisciplinasDoCursoServiceV1;

    // --- ENDPOINT QUE LISTA AS DISCIPLINAS DE UM CURSO DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa as disciplinas de um curso de forma paginada",
            description = "Lista as disciplinas do curso identificado por 'cursoUid'. A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o curso não existir.")
    public PesquisarDisciplinasDoCursoRetornoV1 pesquisar(@Valid @ParameterObject PesquisarDisciplinasDoCursoParamsV1 params) {
        return pesquisarDisciplinasDoCursoServiceV1.pesquisar(params);
    }
}
