package erp.uniaura.services.web.material.pesquisarMateriaisDaTurmaDisciplina.v1;

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
public class PesquisarMateriaisDaTurmaDisciplinaWsV1 extends BaseWs {

    private final PesquisarMateriaisDaTurmaDisciplinaServiceV1 pesquisarMateriaisDaTurmaDisciplinaServiceV1;

    // --- ENDPOINT QUE LISTA OS MATERIAIS DA DISCIPLINA NA TURMA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR", "ROLE_ALUNO", "ROLE_RESPONSAVEL"})
    @Operation(summary = "Pesquisa os materiais de uma disciplina em uma turma",
            description = "Lista os materiais didáticos da disciplina ('disciplinaUid') na turma ('turmaUid'). A paginação usa 'de' e 'qtd'; "
                    + "a ordenação usa 'ordem' (+campo ou -campo), por padrão os mais recentes primeiro. Sem materiais, devolve a lista vazia.")
    public PesquisarMateriaisDaTurmaDisciplinaRetornoV1 pesquisar(@Valid @ParameterObject PesquisarMateriaisDaTurmaDisciplinaParamsV1 params) {
        return pesquisarMateriaisDaTurmaDisciplinaServiceV1.pesquisar(params);
    }
}
