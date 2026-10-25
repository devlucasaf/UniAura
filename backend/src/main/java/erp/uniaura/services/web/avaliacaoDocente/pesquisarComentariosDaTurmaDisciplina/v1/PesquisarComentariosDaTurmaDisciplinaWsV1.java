package erp.uniaura.services.web.avaliacaoDocente.pesquisarComentariosDaTurmaDisciplina.v1;

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
public class PesquisarComentariosDaTurmaDisciplinaWsV1 extends BaseWs {

    private final PesquisarComentariosDaTurmaDisciplinaServiceV1 pesquisarComentariosDaTurmaDisciplinaServiceV1;

    // --- ENDPOINT QUE LISTA OS COMENTÁRIOS ANÔNIMOS DA TURMA/DISCIPLINA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    @Operation(summary = "Pesquisa os comentários anônimos de uma turma e disciplina de forma paginada",
            description = "Lista só as avaliações que têm comentário; o aluno autor não é identificado. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+criadoEm ou -criadoEm). "
                    + "Erro 422 se o vínculo não existir.")
    public PesquisarComentariosDaTurmaDisciplinaRetornoV1 pesquisar(@Valid @ParameterObject PesquisarComentariosDaTurmaDisciplinaParamsV1 params) {
        return pesquisarComentariosDaTurmaDisciplinaServiceV1.pesquisar(params);
    }
}
