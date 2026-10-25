package erp.uniaura.services.web.turma.pesquisarAlunosDaTurma.v1;

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
public class PesquisarAlunosDaTurmaWsV1 extends BaseWs {

    private final PesquisarAlunosDaTurmaServiceV1 pesquisarAlunosDaTurmaServiceV1;

    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa os alunos de uma turma de forma paginada",
            description = "Lista os alunos matriculados na turma identificada por 'turmaUid'. A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se a turma não existir.")
    public PesquisarAlunosDaTurmaRetornoV1 pesquisar(@Valid @ParameterObject PesquisarAlunosDaTurmaParamsV1 params) {
        return pesquisarAlunosDaTurmaServiceV1.pesquisar(params);
    }
}
