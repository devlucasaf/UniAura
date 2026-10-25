package erp.uniaura.services.web.nota.pesquisarNotasDoAluno.v1;

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
public class PesquisarNotasDoAlunoWsV1 extends BaseWs {

    private final PesquisarNotasDoAlunoServiceV1 pesquisarNotasDoAlunoServiceV1;

    // --- ENDPOINT QUE LISTA TODAS AS NOTAS DE UM ALUNO DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa todas as notas de um aluno de forma paginada",
            description = "Lista as notas lançadas para o aluno em todas as turmas. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o aluno não existir.")
    public PesquisarNotasDoAlunoRetornoV1 pesquisar(@Valid @ParameterObject PesquisarNotasDoAlunoParamsV1 params) {
        return pesquisarNotasDoAlunoServiceV1.pesquisar(params);
    }
}
