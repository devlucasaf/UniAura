package erp.uniaura.services.web.aluno.pesquisarAlunos.v1;

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
public class PesquisarAlunosWsV1 extends BaseWs {

    private final PesquisarAlunosServiceV1 pesquisarAlunosServiceV1;

    // --- A EQUIPE ACADÊMICA E OS PROFESSORES LISTAM OS ALUNOS ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa alunos por situação de forma paginada",
            description = "A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo).")
    public PesquisarAlunosRetornoV1 pesquisar(@Valid @ParameterObject PesquisarAlunosParamsV1 params) {
        return pesquisarAlunosServiceV1.pesquisar(params);
    }
}
