package erp.uniaura.services.web.atividade.pesquisarAtividadesDaTurmaDisciplina.v1;

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
public class PesquisarAtividadesDaTurmaDisciplinaWsV1 extends BaseWs {

    private final PesquisarAtividadesDaTurmaDisciplinaServiceV1 pesquisarAtividadesDaTurmaDisciplinaServiceV1;

    // --- A EQUIPE, OS PROFESSORES E OS ALUNOS CONSULTAM AS ATIVIDADES DE UMA TURMA E DISCIPLINA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR", "ROLE_ALUNO"})
    @Operation(summary = "Pesquisa as atividades de uma turma e disciplina de forma paginada",
            description = "Lista as atividades do vínculo informado. A paginação usa 'de' e 'qtd'; a ordenação usa 'ordem' (+campo ou -campo). Erro 422 se o vínculo não existir.")
    public PesquisarAtividadesDaTurmaDisciplinaRetornoV1 pesquisar(@Valid @ParameterObject PesquisarAtividadesDaTurmaDisciplinaParamsV1 params) {
        return pesquisarAtividadesDaTurmaDisciplinaServiceV1.pesquisar(params);
    }
}
