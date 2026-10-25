package erp.uniaura.services.web.aluno.alterarAluno.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AlterarAlunoWsV1 extends BaseWs {

    private final AlterarAlunoServiceV1 alterarAlunoServiceV1;

    // --- A EQUIPE ACADÊMICA ALTERA ALUNOS ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    @Operation(summary = "Altera um aluno",
            description = "Atualiza os dados acadêmicos e os do usuário vinculado. "
                    + "Erro 422 se o aluno ou a turma não existirem ou se a nova matrícula ou o novo e-mail já estiverem em uso.")
    public void alterar(@Valid @RequestBody AlterarAlunoParamsV1 params) {
        alterarAlunoServiceV1.alterar(params);
    }
}
