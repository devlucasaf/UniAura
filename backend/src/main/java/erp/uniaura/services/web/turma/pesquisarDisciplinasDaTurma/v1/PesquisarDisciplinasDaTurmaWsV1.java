package erp.uniaura.services.web.turma.pesquisarDisciplinasDaTurma.v1;

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
public class PesquisarDisciplinasDaTurmaWsV1 extends BaseWs {

    private final PesquisarDisciplinasDaTurmaServiceV1 pesquisarDisciplinasDaTurmaServiceV1;

    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa as disciplinas vinculadas a uma turma",
            description = "Lista as disciplinas da turma identificada por 'turmaUid', com professor, dia e horário, ordenadas por dia da semana e horário de início. "
                    + "Erro 422 se a turma não existir.")
    public PesquisarDisciplinasDaTurmaRetornoV1 pesquisar(@Valid @ParameterObject PesquisarDisciplinasDaTurmaParamsV1 params) {
        return pesquisarDisciplinasDaTurmaServiceV1.pesquisar(params);
    }
}
