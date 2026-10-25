package erp.uniaura.services.web.nota.pesquisarNotasDaTurmaDisciplina.v1;

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
public class PesquisarNotasDaTurmaDisciplinaWsV1 extends BaseWs {

    private final PesquisarNotasDaTurmaDisciplinaServiceV1 pesquisarNotasDaTurmaDisciplinaServiceV1;

    // --- ENDPOINT QUE LISTA AS NOTAS DA DISCIPLINA NA TURMA DE FORMA PAGINADA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    @Operation(summary = "Pesquisa as notas de uma turma em uma disciplina de forma paginada",
            description = "Lista as notas lançadas para a turma e disciplina informadas. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). "
                    + "Erro 422 se a turma ou a disciplina não existirem.")
    public PesquisarNotasDaTurmaDisciplinaRetornoV1 pesquisar(@Valid @ParameterObject PesquisarNotasDaTurmaDisciplinaParamsV1 params) {
        return pesquisarNotasDaTurmaDisciplinaServiceV1.pesquisar(params);
    }
}
