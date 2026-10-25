package erp.uniaura.services.web.frequencia.cadastrarAula.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.LoggedInUserId;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class CadastrarAulaWsV1 extends BaseWs {

    private final CadastrarAulaServiceV1 cadastrarAulaServiceV1;

    // --- ENDPOINT QUE CADASTRA UMA AULA E GERA A FREQUÊNCIA DOS ALUNOS ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    @Operation(summary = "Cadastra uma aula e gera a frequência dos alunos",
            description = "Cria a aula e um registro de presença (presente) para cada aluno com matrícula ATIVA na turma; devolve o uid da aula e a quantidade de alunos. "
                    + "Só o professor responsável pela disciplina, a coordenação ou o admin podem cadastrar. "
                    + "Erro 422 se o vínculo ou o professor informado não existirem, se o vínculo não tiver professor responsável ou se o usuário não for o professor responsável.")
    public CadastrarAulaRetornoV1 cadastrar(@LoggedInUserId String usuarioId, @Valid @RequestBody CadastrarAulaParamsV1 params) {
        return cadastrarAulaServiceV1.cadastrar(Long.valueOf(usuarioId), params);
    }
}
